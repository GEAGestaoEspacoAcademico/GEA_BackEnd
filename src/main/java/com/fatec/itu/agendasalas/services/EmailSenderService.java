package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Usuario;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("{spring.mail.username}")
    private String hostEmail;
    
    public void enviarEmailResetSenha(Usuario usuario, String link) throws MessagingException {
       
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(usuario.getEmail());
        helper.setFrom(hostEmail);
        

            String html = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8" />
                <style>
                    body {
                        background-color: #F8F8FF;
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                    }

                    .container {
                        width: 100%%;
                        padding: 30px 0;
                        display: flex;
                        justify-content: center;
                    }

                    .card {
                        background-color: #E6E6E6;
                        width: 420px;
                        padding: 25px;
                        border-radius: 12px;
                    }

                    h2 {
                        color: #333;
                        text-align: center;
                    }

                    p {
                        color: #444;
                        font-size: 15px;
                        line-height: 1.5;
                    }

                    .btn {
                        display: block;
                        width: 100%%;
                        background-color: #F21E18;
                        text-align: center;
                        padding: 14px 0;
                        border-radius: 8px;
                        color: white !important;
                        text-decoration: none;
                        font-weight: bold;
                        font-size: 15px;
                        margin: 25px 0;
                    }

                    .btn:hover {
                        background-color: #D4120C;
                    }

                    .footer {
                        margin-top: 20px;
                        font-size: 12px;
                        color: #777;
                        text-align: center;
                    }
                </style>
            </head>
            <body>

            <div class="container">
                <div class="card">

                    <h2>Recuperação de Senha</h2>

                    <p>Olá, <b>%s</b>!</p>

                    <p>
                        Recebemos uma solicitação para redefinir sua senha.<br>
                        Para continuar, clique no botão abaixo:
                    </p>

                    <a class="btn" href="%s" target="_blank">
                        Clique aqui para redefinir sua senha
                    </a>

                    <p>
                        Se você não solicitou isso, ignore este e-mail.
                    </p>

                    <p class="footer">
                        Por favor, não responder.<br>
                        Esta é uma mensagem automática.
                    </p>

                </div>
            </div>

            </body>
            </html>
    """.formatted(usuario.getNome(), link);
        helper.setSubject("Recuperação de senha - GEA Fatec Itu");
        helper.setText(html, true);

        mailSender.send(message);

    }
}

