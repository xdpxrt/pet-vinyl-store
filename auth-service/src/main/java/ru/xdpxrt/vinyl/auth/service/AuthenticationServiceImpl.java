package ru.xdpxrt.vinyl.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.auth.mapper.AuthMapper;
import ru.xdpxrt.vinyl.auth.model.AuthenticationRequest;
import ru.xdpxrt.vinyl.auth.model.AuthenticationResponse;
import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
import ru.xdpxrt.vinyl.config.JWTService;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.service.UserFeignService;
import ru.xdpxrt.vinyl.user.model.User;

import static ru.xdpxrt.vinyl.cons.Message.USER_BY_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserFeignService userFeignService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        InboundUserDTO userDTO = InboundUserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        AuthUserDTO user = userFeignService.addUser(userDTO);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        AuthUserDTO user = userFeignService.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}