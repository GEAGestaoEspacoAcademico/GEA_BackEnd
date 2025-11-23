package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Usuario;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailResetSenha(Usuario usuario, String link) {
       
        SimpleMailMessage email = new SimpleMailMessage();

    }
}

