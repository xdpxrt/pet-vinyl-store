package ru.xdpxrt.vinyl.user.service;

import org.springframework.security.core.Authentication;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.FullUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;

public interface UserService {
    FullUserDTO addUser(InboundUserDTO inboundUserDTO);

    FullUserDTO updateUser(InboundUserDTO inboundUserDTO, Long id, Authentication authentication);

    FullUserDTO getUser(Long id, Authentication authentication);

    UserDTO getUser(String email);

    ShortUserDTO getShortUser(Long id);

    void deleteUser(Long userId, Authentication authentication);
}