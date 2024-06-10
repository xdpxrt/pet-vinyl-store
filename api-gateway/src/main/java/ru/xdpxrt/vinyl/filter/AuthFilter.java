package ru.xdpxrt.vinyl.filter;

import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.xdpxrt.vinyl.handler.UnauthorizedException;
import ru.xdpxrt.vinyl.service.JWTService;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthFilter implements GatewayFilter {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private JWTService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if (jwtService.isExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//                if (!exchange.getRequest().getHeaders().containsKey(AUTHORIZATION))
//                    throw new UnauthorizedException("Missing authorization header");
//                String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
//                if (authHeader != null && authHeader.startsWith("Bearer "))
//                    authHeader = authHeader.substring(7);
//                try {
//                    jwtService.validateToken(authHeader);
//                } catch (Exception e) {
//                    throw new UnauthorizedException("Unauthorized access to application");
//                }
//            }
//            return chain.filter(exchange);
//        };
//    }
}