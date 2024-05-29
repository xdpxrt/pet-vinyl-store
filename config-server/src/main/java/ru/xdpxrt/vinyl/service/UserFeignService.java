package ru.xdpxrt.vinyl.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;

import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.cons.URI.USER_URI;

@Validated
@FeignClient("USER-SERVICE")
public interface UserFeignService {

    @GetMapping(USER_URI + ID_URI)
    UserDTO getUserById(@PathVariable @Positive Long id);

    @GetMapping(USER_URI)
    UserDTO getUserByEmail(@RequestParam @Email String email);
}