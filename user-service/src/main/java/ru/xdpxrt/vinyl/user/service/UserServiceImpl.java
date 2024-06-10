package ru.xdpxrt.vinyl.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.dto.messageDTO.MessageDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.FullUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.handler.NotFoundException;
import ru.xdpxrt.vinyl.service.OrderFeignService;
import ru.xdpxrt.vinyl.user.mapper.UserMapper;
import ru.xdpxrt.vinyl.user.model.User;
import ru.xdpxrt.vinyl.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.xdpxrt.vinyl.cons.Config.BIRTHDAY_TOPIC;
import static ru.xdpxrt.vinyl.cons.Message.USER_BY_EMAIL_NOT_FOUND;
import static ru.xdpxrt.vinyl.cons.Message.USER_NOT_FOUND;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final OrderFeignService orderFeignService;
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Scheduled(cron = "0 0 7 * * *")
    public void sendCongrats() {
        LocalDate date = LocalDate.now();
        Specification<User> spec = (root, query, cb) -> cb.and(
                cb.equal(cb.function("day", Integer.class, root.get("birthday")), date.getDayOfMonth()),
                cb.equal(cb.function("month", Integer.class, root.get("birthday")), date.getMonth()));
        List<User> users = userRepository.findAll(spec);
        users.forEach(u -> kafkaTemplate.send(BIRTHDAY_TOPIC, getCongratsMessageDTO(u)));
    }

    @Override
    @Transactional
    public FullUserDTO addUser(InboundUserDTO inboundUserDTO) {
        log.debug("Adding user {}", inboundUserDTO);
        User user = userRepository.save(userMapper.toUser(inboundUserDTO));
        log.debug("User is added {}", user);
        return userMapper.toFullUserDTO(user);
    }

    @Override
    @Transactional
    public FullUserDTO updateUser(InboundUserDTO inboundUserDTO, Long userId) {
        log.debug("Updating user ID{}", userId);
        User user = getUserIfExists(userId);
        if (inboundUserDTO.getName() != null) user.setName(inboundUserDTO.getName());
        if (inboundUserDTO.getEmail() != null) user.setEmail(inboundUserDTO.getEmail());
        if (inboundUserDTO.getBirthday() != null) user.setBirthday(inboundUserDTO.getBirthday());
        user = userRepository.save(user);
        log.debug("User ID{} updated", userId);
        return userMapper.toFullUserDTO(user);
    }

    @Override
    public FullUserDTO getUser(Long userId) {
        log.debug("Getting user ID{}", userId);
        List<ShortOrderDTO> orders = orderFeignService.getOrdersByCustomer(userId);
        FullUserDTO user = userMapper.toFullUserDTO(getUserIfExists(userId));
        user.setOrders(orders);
        return user;
    }

    @Override
    public UserDTO getUser(String email) {
        log.debug("Getting user by email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format(USER_BY_EMAIL_NOT_FOUND, email)));
        return userMapper.toUserDTO(user);
    }

    @Override
    public ShortUserDTO getShortUser(Long userId) {
        log.debug("Getting user ID{}", userId);
        User user = getUserIfExists(userId);
        return userMapper.toShortUserDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.debug("Deleting user ID{}", userId);
        getUserIfExists(userId);
        userRepository.deleteById(userId);
        log.debug("User ID{} is deleted", userId);
    }

    private User getUserIfExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format(USER_NOT_FOUND, userId)));
    }

    private MessageDTO getCongratsMessageDTO(User user) {
        String message = """
                    Dear, %s.
                If you’re reading this this… Congratulations, you’re alive.
                If that’s not something to smile about, then I don’t know what is...
                Best wishes from your favourite Vinyl Store""";
        return MessageDTO.builder()
                .email(user.getEmail())
                .message(String.format(message, user.getName()))
                .build();
    }
}