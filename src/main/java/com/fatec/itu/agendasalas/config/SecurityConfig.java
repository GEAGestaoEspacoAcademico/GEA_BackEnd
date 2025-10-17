package com.fatec.itu.agendasalas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                
                .requestMatchers("/h2-console/**").permitAll() // libera o H2
                .requestMatchers("/usuarios/**").permitAll()//Por enquanto apenas para teste.
                .requestMatchers("/professores/**").permitAll()//Por enquanto apenas para teste.
                .requestMatchers("/auth/**").permitAll() 
                
            )
            .csrf(csrf -> csrf.disable()) // desabilita CSRF para o console
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // permite iframes
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}