package ru.xdpxrt.vinyl.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.service.OrderFeignService;
import ru.xdpxrt.vinyl.user.mapper.UserMapper;
import ru.xdpxrt.vinyl.user.model.User;
import ru.xdpxrt.vinyl.user.repository.UserRepository;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final OrderFeignService orderFeignService;

    @Override
    @Transactional
    public UserDTO addUser(InboundUserDTO inboundUserDTO) {
        log.debug("Adding user {}", inboundUserDTO);
        User user = userRepository.save(userMapper.toUser(inboundUserDTO));
        log.debug("User is added {}", user);
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(InboundUserDTO inboundUserDTO, Long userId) {
        log.debug("Updating user ID{}", userId);
        User user = getUserIfExists(userId);
        if (inboundUserDTO.getName() != null) user.setName(inboundUserDTO.getName());
        if (inboundUserDTO.getEmail() != null) user.setEmail(inboundUserDTO.getEmail());
        if (inboundUserDTO.getBirthday() != null) user.setBirthday(inboundUserDTO.getBirthday());
        user = userRepository.save(user);
        log.debug("User ID{} updated", userId);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getUser(Long userId) {
        log.debug("Getting user ID{}", userId);
        List<ShortOrderDTO> orders = orderFeignService.getOrdersByCustomer(userId);
        UserDTO user =  userMapper.toUserDTO(getUserIfExists(userId));
        user.setOrders(orders);
        return user;
    }

    @Override
    public UserDTO getUser(String email) {
        log.debug("Getting user by email {}", email);
        return userMapper.toUserDTO(userRepository.findByEmail(email));
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
}
