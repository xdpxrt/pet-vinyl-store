//package ru.xdpxrt.vinyl.auth.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import ru.xdpxrt.vinyl.auth.model.AuthenticationRequest;
//import ru.xdpxrt.vinyl.auth.model.AuthenticationResponse;
//import ru.xdpxrt.vinyl.auth.model.RegisterRequest;
//import ru.xdpxrt.vinyl.config.JWTService;
//import ru.xdpxrt.vinyl.error.NotFoundException;
//import ru.xdpxrt.vinyl.user.model.User;
//import ru.xdpxrt.vinyl.user.repository.UserRepository;
//
//import static ru.xdpxrt.vinyl.cons.Message.USER_BY_EMAIL_NOT_FOUND;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JWTService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    public AuthenticationResponse register(RegisterRequest request) {
//        User user = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
//        userRepository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    @Override
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
//                new NotFoundException(String.format(USER_BY_EMAIL_NOT_FOUND, request.getEmail())));
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//}