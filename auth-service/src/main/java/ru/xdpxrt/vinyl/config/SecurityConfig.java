//package ru.xdpxrt.vinyl.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//import static ru.xdpxrt.vinyl.cons.URI.*;
//import static ru.xdpxrt.vinyl.user.model.Role.ADMIN;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JWTAuthenticationFilter jwtAuthFilter;
//    private final AuthenticationProvider authProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req -> req
//                        .requestMatchers(AUTH_URI).permitAll()
//                        .requestMatchers(WHITE_LIST_URI).hasRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, WHITE_LIST_URI).permitAll()
//                        .requestMatchers(UNIT_URI + "/**").hasRole(ADMIN.name())
//                        .requestMatchers(USER_URI + "/**").authenticated()
//                        .anyRequest()
//                        .authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}