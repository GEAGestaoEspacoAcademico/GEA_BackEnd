package com.fatec.itu.agendasalas.jwt;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.services.UserDetailsServiceImpl;

import static com.fatec.itu.agendasalas.jwt.SecurityConstants.HEADER_STRING;
import static com.fatec.itu.agendasalas.jwt.SecurityConstants.SECRET;
import static com.fatec.itu.agendasalas.jwt.SecurityConstants.TOKEN_PREFIX;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserDetailsServiceImpl userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsServiceImpl userDetailsServiceImpl) {
        super(authManager);
        this.userDetailsService = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
          String token = request.getHeader(HEADER_STRING);

    if (token != null) {
        try {
            var verifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
            var decoded = verifier.verify(token.replace(TOKEN_PREFIX, ""));
            String login = decoded.getSubject();
        

            if (login != null) {
               UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                System.out.println("Authorities do usuário: " + userDetails.getAuthorities());
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        } catch (Exception e) {
            return null;
        }
    }
    return null;
}
}