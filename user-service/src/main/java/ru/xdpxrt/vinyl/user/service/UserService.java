package ru.xdpxrt.vinyl.user.service;

import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

public interface UserService {
    UserDTO addUser(InboundUserDTO inboundUserDTO);

    UserDTO updateUser(InboundUserDTO inboundUserDTO, Long id);

    UserDTO getUser(Long id);

    UserDTO getUser(String email);

    void deleteUser(Long userId);
}