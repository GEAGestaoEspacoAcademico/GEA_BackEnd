package com.fatec.itu.agendasalas.jwt;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatec.itu.agendasalas.entity.Usuario;
import static com.fatec.itu.agendasalas.jwt.SecurityConstants.EXPIRATION_TIME;
import static com.fatec.itu.agendasalas.jwt.SecurityConstants.SECRET;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/agendasalas/controllers/AuthController/login");
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest req, HttpServletResponse res){
        try {
            Usuario creds = new ObjectMapper()
            .readValue(req.getInputStream(), Usuario.class);
            
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(creds.getNome(), creds.getPassword(), creds.getAuthorities())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication  (HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException{
        String token = JWT.create()
        .withSubject(((Usuario) auth.getPrincipal()).getNome())
        .withClaim("cargo", ((Usuario)auth.getPrincipal()).getCargo().getNome())
        .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .sign(Algorithm.HMAC512(SECRET.getBytes()));


        String body = (((Usuario)auth.getPrincipal()).getNome()) + " " + token;
        res.getWriter().write(body);
        res.getWriter().flush();
    }

}

