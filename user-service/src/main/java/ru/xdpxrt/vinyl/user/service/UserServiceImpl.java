package ru.xdpxrt.vinyl.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.cons.Role;
import ru.xdpxrt.vinyl.dto.messageDTO.MessageDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.dto.userDTO.FullUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.handler.ForbiddenException;
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
    @CachePut(cacheNames = "user::getById", key = "#id")
    public FullUserDTO updateUser(InboundUserDTO inboundUserDTO, Long id, Authentication authentication) {
        log.debug("Updating user ID{}", id);
        User user = getUserIfExists(id);
        checkAccess(user, authentication);
        if (inboundUserDTO.getName() != null) user.setName(inboundUserDTO.getName());
        if (inboundUserDTO.getEmail() != null) user.setEmail(inboundUserDTO.getEmail());
        if (inboundUserDTO.getBirthday() != null) user.setBirthday(inboundUserDTO.getBirthday());
        user = userRepository.save(user);
        log.debug("User ID{} updated", id);
        return userMapper.toFullUserDTO(user);
    }

    @Override
    @Cacheable(cacheNames = "user::getById", key = "#id")
    public FullUserDTO getUser(Long id, Authentication authentication) {
        log.debug("Getting user ID{}", id);
        User user = getUserIfExists(id);
        checkAccess(user, authentication);
        FullUserDTO userDTO = userMapper.toFullUserDTO(user);
        List<ShortOrderDTO> orders = orderFeignService.getOrdersByCustomer(id);
        userDTO.setOrders(orders);
        return userDTO;
    }

    @Override
    @Cacheable(cacheNames = "user::getByEmail", key = "#email")
    public UserDTO getUser(String email, Authentication authentication) {
        log.debug("Getting user by email {}", email);
        User user = getUserByEmailIfExists(email);
        checkAccess(user, authentication);
        return userMapper.toUserDTO(user);
    }

    @Override
    public ShortUserDTO getShortUser(Long userId, Authentication authentication) {
        log.debug("Getting user ID{}", userId);
        User user = getUserIfExists(userId);
        checkAccess(user, authentication);
        return userMapper.toShortUserDTO(user);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "user::getById", key = "#id")
    public void deleteUser(Long id, Authentication authentication) {
        log.debug("Deleting user ID{}", id);
        User user = getUserIfExists(id);
        checkAccess(user, authentication);
        userRepository.deleteById(id);
        log.debug("User ID{} is deleted", id);
    }

    private User getUserIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    private User getUserByEmailIfExists(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format(USER_BY_EMAIL_NOT_FOUND, email)));
    }

    private void checkAccess(User user, Authentication authentication) {
        String email = authentication.getName();
        String authorities = authentication.getAuthorities().toString();
        String role = authorities.substring(1, authorities.length() - 1);
        if (!user.getEmail().equals(email) && !Role.ADMIN.name().equals(role)) {
            throw new ForbiddenException("Only admins or owner has access");
        }
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