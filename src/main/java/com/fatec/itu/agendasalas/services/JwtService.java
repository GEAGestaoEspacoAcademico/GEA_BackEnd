package com.fatec.itu.agendasalas.services;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Usuario usuario){
        try {
            return Jwts.builder()
            .setIssuer("agendasalas-fatec")
            .setSubject(usuario.getLogin())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+expiration))
            .claim("nome", usuario.getNome())
            .claim("cargo", usuario.getCargo().getNome())
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
        } catch (Exception e) {
            throw new JwtException("Erro ao gerar o JWT TOKEN", e.getCause());
        }
               
    }

 
    

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
            final String extractedUsername = extractUsername(token);
            return extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }   


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
