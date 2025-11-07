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
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationDTO;
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
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest req, HttpServletResponse res){
        try {
            UsuarioAuthenticationDTO creds = new ObjectMapper()
            .readValue(req.getInputStream(), UsuarioAuthenticationDTO.class);
            
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(creds.login(), creds.senha())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication  (HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException{
        
        Usuario usuario = (Usuario) auth.getPrincipal();

        String token = JWT.create()
        .withSubject(usuario.getLogin())
        .withClaim("nome", usuario.getNome())
        .withClaim("cargo", usuario.getCargo().getNome().toUpperCase())
        .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .sign(Algorithm.HMAC512(SECRET.getBytes()));


        
        res.setContentType("application/json");
        res.getWriter().write("{"
            + "\"login\":\"" + usuario.getLogin() + "\","
            + "\"nome\":\"" + usuario.getNome() + "\","
            + "\"cargo\":\"" + usuario.getCargo().getNome().toUpperCase() + "\","
            + "\"token\":\"" + token + "\""
            + "}");
            
        res.getWriter().flush();
    }

}

