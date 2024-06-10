package ru.xdpxrt.vinyl.auth.service;

import ru.xdpxrt.vinyl.auth.model.AuthUser;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

public class Mapper {

    public static AuthUser toAuthUser(UserDTO userDTO) {
        return new AuthUser(
                userDTO.getId(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole());
    }
}