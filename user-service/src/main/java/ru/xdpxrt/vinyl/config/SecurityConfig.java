package ru.xdpxrt.vinyl.config;

import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.xdpxrt.vinyl.filter.JWTFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static ru.xdpxrt.vinyl.cons.URI.USER_URI;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(r -> r.requestMatchers(HttpMethod.POST, USER_URI)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}