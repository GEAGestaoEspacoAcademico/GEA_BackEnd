package com.fatec.itu.agendasalas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/usuarios/**").permitAll() // libera o H2
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // desabilita CSRF para o console
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // permite iframes
        return http.build();
    }
}