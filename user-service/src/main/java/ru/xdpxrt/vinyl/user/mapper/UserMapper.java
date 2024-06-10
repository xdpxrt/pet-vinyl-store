package ru.xdpxrt.vinyl.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.FullUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(InboundUserDTO inboundUserDTO);

    @Mapping(target = "orders", ignore = true)
    FullUserDTO toFullUserDTO(User user);

    ShortUserDTO toShortUserDTO(User user);

    UserDTO toUserDTO(User user);
}