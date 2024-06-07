package ru.xdpxrt.vinyl.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

import static ru.xdpxrt.vinyl.cons.URI.*;
import static ru.xdpxrt.vinyl.cons.URI.SHORT_URI;

@Validated
@FeignClient("USER-SERVICE")
public interface UserFeignService {

    @PostMapping(USER_URI)
    AuthUserDTO addUser(@RequestBody @Valid InboundUserDTO inboundUserDTO);

    @GetMapping(USER_URI + ID_URI)
    UserDTO getUserById(@PathVariable @Positive Long id);

    @GetMapping(USER_URI)
    AuthUserDTO getUserByEmail(@RequestParam @Email String email);

    @GetMapping(USER_URI + ID_URI + SHORT_URI)
    ShortUserDTO getShortUser(@PathVariable @Positive Long id);
}