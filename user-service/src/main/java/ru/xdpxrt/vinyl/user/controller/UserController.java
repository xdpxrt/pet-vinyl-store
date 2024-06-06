package ru.xdpxrt.vinyl.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.user.service.UserService;

import static ru.xdpxrt.vinyl.cons.URI.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_URI)
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody @Valid InboundUserDTO inboundUserDTO) {
        log.info("Response from POST request on {}", USER_URI);
        return userService.addUser(inboundUserDTO);
    }

    @PatchMapping(ID_URI)
    public UserDTO updateUser(@RequestBody @Valid InboundUserDTO inboundUserDTO,
                              @PathVariable @Positive Long id) {
        log.info("Response from PATCH request on {}", USER_URI);
        return userService.updateUser(inboundUserDTO, id);
    }

    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long id) {
        log.info("Response from DELETE request on {}/{}", USER_URI, id);
        userService.deleteUser(id);
    }

    @GetMapping(ID_URI)
    public UserDTO getUserById(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", USER_URI, id);
        return userService.getUser(id);
    }

    @GetMapping()
    public AuthUserDTO getUserByEmail(@RequestParam @Email String email) {
        log.info("Response from GET request on {}/{}", USER_URI, email);
        return userService.getUser(email);
    }

    @GetMapping(ID_URI + SHORT_URI)
    public ShortUserDTO getShortUser(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}/{}", USER_URI, id, SHORT_URI);
        return userService.getShortUser(id);
    }
}