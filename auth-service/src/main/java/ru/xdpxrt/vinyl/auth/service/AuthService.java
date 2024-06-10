package ru.xdpxrt.vinyl.auth.service;

import ru.xdpxrt.vinyl.auth.model.AuthRequest;
import ru.xdpxrt.vinyl.auth.model.AuthResponse;
import ru.xdpxrt.vinyl.auth.model.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse authenticate(AuthRequest authRequest);
}