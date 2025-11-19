package com.fatec.itu.agendasalas.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fatec.itu.agendasalas.services.JwtService;
import com.fatec.itu.agendasalas.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
       
            var token = this.recoverToken(request);
            if(token!=null){
               var username = jwtService.extractUsername(token);
               UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
               
               if(jwtService.isTokenValid(token, userDetails)){
                    UsernamePasswordAuthenticationToken auth = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
               

            }
            //ignora o filtro caso seja nulo o token e vai pro proximo
            filterChain.doFilter(request, response);
       
    }

    
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        
        if(authHeader==null) return null;
        return authHeader.replace("Bearer ", "");
    }
    
}
