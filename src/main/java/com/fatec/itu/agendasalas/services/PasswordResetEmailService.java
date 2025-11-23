package com.fatec.itu.agendasalas.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.ResetSenhaResponseDTO;
import com.fatec.itu.agendasalas.entity.PasswordResetToken;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.PasswordResetTokenRepository;

@Service
public class PasswordResetEmailService {
 
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailSenderService emailSenderService;

    public String gerarTokenRedefinicaoPorEmail(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken reset = new PasswordResetToken(usuario, token);
        
        passwordResetTokenRepository.save(reset);
        return token;
    }

    public ResetSenhaResponseDTO solicitarResetDeSenha(String email) {
        
        Usuario usuario = usuarioService.buscarUsuarioPeloEmail(email);
        if(usuario!=null){
            String token = gerarTokenRedefinicaoPorEmail(usuario);
         

            String link = "http://localhost:4200/esqueci-senha?token=" + token;
            emailSenderService.enviarEmailResetSenha(usuario, link);    
        }
       
        return new ResetSenhaResponseDTO("Se o seu e-mail existir, enviaremos um link de confirmação");
    }

    public String validarPasswordResetToken(String token){
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if(!isTokenExistente(passToken)){
            return "Token inválido";
        }

        if(isTokenExpirado(passToken)){
            return "Token expirado";
        }
        return null;
    } 

    private boolean isTokenExistente(PasswordResetToken passToken){
        return passToken!=null;
    }
    
    private boolean isTokenExpirado(PasswordResetToken passToken){
        return passToken.getDataExpiracao().toInstant().isBefore(Instant.now());
    }
}
