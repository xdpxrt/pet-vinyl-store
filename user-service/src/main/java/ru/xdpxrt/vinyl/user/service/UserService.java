package ru.xdpxrt.vinyl.user.service;

import org.springframework.security.core.Authentication;
import ru.xdpxrt.vinyl.dto.userDTO.FullUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

public interface UserService {
    FullUserDTO addUser(InboundUserDTO inboundUserDTO);

    FullUserDTO updateUser(InboundUserDTO inboundUserDTO, Long id, Authentication authentication);

    FullUserDTO getUser(Long id, Authentication authentication);

    UserDTO getUser(String email, Authentication authentication);

    ShortUserDTO getShortUser(Long id, Authentication authentication);

    void deleteUser(Long userId, Authentication authentication);
}