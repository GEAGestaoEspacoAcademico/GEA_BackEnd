package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Usuario;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("{spring.mail.username}")
    private String hostEmail;
    
    public void enviarEmailResetSenha(Usuario usuario, String link) {
       
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usuario.getEmail());
        email.setFrom(hostEmail);
        email.setSubject("Recuperação de senha - GEA Fatec Itu");
        email.setText("Olá, " + usuario.getNome() + "!\n\n" +
        "Recebemos uma solicitação para redefinir sua senha.\n"+
        "Para alterar sua senha clique no link abaixo:\n\n"+
        link+"\n\n"+
        "Se você não solicitou isso, apenas ignore este e-mail.\n\n" +
        "Por favor, não responder! Esta é uma mensagem automática."
    );

    mailSender.send(email);

    }
}

