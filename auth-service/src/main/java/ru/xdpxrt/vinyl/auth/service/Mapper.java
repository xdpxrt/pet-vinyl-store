package ru.xdpxrt.vinyl.auth.service;

import ru.xdpxrt.vinyl.auth.model.AuthUser;
import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

public class Mapper {

    public static AuthUser toAuthUser(UserDTO userDTO) {
        return new AuthUser(
                userDTO.getId(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole());
    }

    public static InboundUserDTO toInboundUserDTO(RegisterRequest request) {
        return InboundUserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }
}