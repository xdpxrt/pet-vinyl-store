package ru.xdpxrt.vinyl.user.mapper;

import org.mapstruct.Mapper;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(InboundUserDTO inboundUserDTO);

    AuthUserDTO toAuthUserDTO(User user);

    UserDTO toUserDTO(User user);
}