//package ru.xdpxrt.vinyl.auth.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import ru.xdpxrt.vinyl.auth.model.AuthenticationRequest;
//import ru.xdpxrt.vinyl.auth.model.AuthenticationResponse;
//import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
//import ru.xdpxrt.vinyl.auth.service.AuthenticationService;
//
//import static ru.xdpxrt.vinyl.cons.URI.*;
//
//@Slf4j
//@Validated
//@RestController
//@RequestMapping(AUTH_URI)
//@RequiredArgsConstructor
//public class AuthenticationController {
//    private final AuthenticationService service;
//
//    @PostMapping(REGISTER_URI)
//    @ResponseStatus(HttpStatus.CREATED)
//    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request) {
//        log.info("Response from POST request on {}", AUTH_URI + REGISTER_URI);
//        return service.register(request);
//    }
//
//    @PostMapping(AUTHENTICATE_URI)
//    @ResponseStatus(HttpStatus.CREATED)
//    public AuthenticationResponse register(@RequestBody @Valid AuthenticationRequest request) {
//        log.info("Response from POST request on {}", AUTH_URI + AUTHENTICATE_URI);
//        return service.authenticate(request);
//    }
//}