package ru.xdpxrt.vinyl.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.auth.model.AuthUser;
import ru.xdpxrt.vinyl.auth.model.AuthRequest;
import ru.xdpxrt.vinyl.auth.model.AuthResponse;
import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
import ru.xdpxrt.vinyl.config.JWTService;
import ru.xdpxrt.vinyl.dto.userDTO.InboundUserDTO;
import ru.xdpxrt.vinyl.handler.UnauthorizedException;
import ru.xdpxrt.vinyl.service.UserFeignService;

import static ru.xdpxrt.vinyl.auth.service.Mapper.toAuthUser;
import static ru.xdpxrt.vinyl.auth.service.Mapper.toInboundUserDTO;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserFeignService userFeignService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        InboundUserDTO userDTO = toInboundUserDTO(request);
        userDTO.setPassword(passwordEncoder.encode(request.getPassword()));
        userFeignService.addUser(userDTO);
        return getAuthenticationResponse(userDTO.getEmail());
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        if (authentication.isAuthenticated()) return getAuthenticationResponse(request.getEmail());
        else throw new UnauthorizedException("Invalid email or password");
    }

    private AuthResponse getAuthenticationResponse(String email) {
        AuthUser user = toAuthUser(userFeignService.getUserByEmail(email));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}