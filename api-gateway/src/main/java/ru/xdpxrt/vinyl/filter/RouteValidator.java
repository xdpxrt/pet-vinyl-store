package ru.xdpxrt.vinyl.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static ru.xdpxrt.vinyl.cons.URI.AUTH_URI;
import static ru.xdpxrt.vinyl.cons.URI.EUREKA_URI;

@Service
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            AUTH_URI + "/**",
            EUREKA_URI);

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}