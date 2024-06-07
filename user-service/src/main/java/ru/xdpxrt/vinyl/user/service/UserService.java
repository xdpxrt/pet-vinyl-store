package ru.xdpxrt.vinyl.user.service;

import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

public interface UserService {
    AuthUserDTO addUser(InboundUserDTO inboundUserDTO);

    UserDTO updateUser(InboundUserDTO inboundUserDTO, Long id);

    UserDTO getUser(Long id);

    AuthUserDTO getUser(String email);

    ShortUserDTO getShortUser(Long id);

    void deleteUser(Long userId);
}