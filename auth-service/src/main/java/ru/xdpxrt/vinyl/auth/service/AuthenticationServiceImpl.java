package ru.xdpxrt.vinyl.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.auth.model.AuthUser;
import ru.xdpxrt.vinyl.auth.model.AuthenticationRequest;
import ru.xdpxrt.vinyl.auth.model.AuthenticationResponse;
import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
import ru.xdpxrt.vinyl.config.JWTService;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.service.UserFeignService;

import static ru.xdpxrt.vinyl.auth.service.Mapper.toAuthUser;

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
        userFeignService.addUser(userDTO);
        AuthUser user = toAuthUser(userFeignService.getUserByEmail(userDTO.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        AuthUser user = toAuthUser(userFeignService.getUserByEmail(request.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}