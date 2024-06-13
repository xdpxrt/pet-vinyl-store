package ru.xdpxrt.vinyl.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import ru.xdpxrt.vinyl.handler.UnauthorizedException;
import ru.xdpxrt.vinyl.service.JWTService;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private JWTService jwtService;

    public AuthFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(AUTHORIZATION)) {
                    throw new UnauthorizedException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtService.validateToken(authHeader);
                } catch (Exception e) {
                    throw new UnauthorizedException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }
}