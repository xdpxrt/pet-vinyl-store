package ru.xdpxrt.vinyl.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(InboundUserDTO inboundUserDTO);

    AuthUserDTO toAuthUserDTO(User user);

    @Mapping(target = "orders", ignore = true)
    UserDTO toUserDTO(User user);

    ShortUserDTO toShortUserDTO(User user);
}