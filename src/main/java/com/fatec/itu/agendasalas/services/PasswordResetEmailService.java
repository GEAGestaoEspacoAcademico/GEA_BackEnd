package com.fatec.itu.agendasalas.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.PasswordResetToken;
import com.fatec.itu.agendasalas.entity.Usuario;

@Service
public class PasswordResetEmailService {
 
    public PasswordResetToken gerarTokenRedefinicaoPorEmail(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        return new PasswordResetToken(usuario, token);
    }

}
