package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Usuario;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("{spring.mail.username}")
    private String hostEmail;
    
    @Async
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
                    <h1> E-MAIL DE TESTE DO SISTEMA GEA </h1>
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

    @Async
    public void enviarNotificacaoAgendamento(LocalDate diaInicial, LocalDate diaFinal, Set<JanelasHorario> janelas, Disciplina disciplina, Usuario remetente, Usuario destinatario, String diaDaSemana) throws MessagingException{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
        helper.setTo(destinatario.getEmail());
        helper.setFrom(hostEmail);
        helper.setSubject("Agendamento de aula");

 
        String horariosFormatados = formatarHorarios(janelas);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataInicialFormatada = diaInicial.format(formatter);
        String dataFinalFormatada = diaFinal.format(formatter);

        String mensagem = montarMensagemAgendamento(disciplina, dataInicialFormatada, dataFinalFormatada, horariosFormatados, remetente, destinatario, diaDaSemana);
        helper.setText(mensagem, true);
        mailSender.send(message);
      
    }

    private String formatarHorarios(Set<JanelasHorario> janelasHorarios){
        StringBuilder horarios = new StringBuilder();
        

        janelasHorarios.forEach((j) -> {
            horarios.append(j.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")))
            .append("-")
            .append(j.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")))
            .append("<br>");
        });
        if(horarios.length()>0){
            horarios.setLength(horarios.length()-4);
        }

        return horarios.toString();
    }
 
    private String montarMensagemAgendamento(Disciplina disciplina, String dataInicial,
                                         String dataFinal, String horariosFormatados,
                                         Usuario remetente, Usuario destinatario, String diaDaSemana) {
    
    // Obter informações da disciplina (ajuste conforme seu modelo)
    String nomeProfessor = disciplina.getProfessor() != null ? 
                          disciplina.getProfessor().getNome() : "Não informado";
    String nomeCurso = disciplina.getCurso() != null ? 
                      disciplina.getCurso().getNomeCurso() : "Não informado";
    String nomeRemetente = remetente != null ? remetente.getNome() : "Sistema";

    return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        margin: 0;
                        padding: 20px;
                        background-color: #f8f9fa;
                    }
                    .email-container {
                        max-width: 600px;
                        margin: 0 auto;
                        background-color: white;
                        padding: 30px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                    }
                    .header {
                        text-align: center;
                        padding-bottom: 20px;
                        border-bottom: 2px solid #F21E18;
                        margin-bottom: 25px;
                    }
                    .header h3 {
                        color: #2c3e50;
                        margin: 0;
                    }
                    .section {
                        margin-bottom: 25px;
                    }
                    .section-title {
                        background-color: #F21E18;
                        color: white;
                        padding: 10px 15px;
                        border-radius: 5px;
                        margin-bottom: 15px;
                        font-weight: bold;
                    }
                    .info-grid {
                        display: grid;
                        grid-template-columns: 150px 1fr;
                        gap: 10px;
                        margin-bottom: 10px;
                    }
                    .label {
                        font-weight: bold;
                        color: #555;
                    }
                    .value {
                        color: #333;
                    }
                    .horarios-container {
                        background-color: #f8f9fa;
                        padding: 15px;
                        border-radius: 5px;
                        border-left: 4px solid #2196F3;
                        margin-top: 10px;
                    }
                    .footer {
                        margin-top: 30px;
                        padding-top: 20px;
                        border-top: 1px solid #ddd;
                        font-size: 12px;
                        color: #777;
                        text-align: center;
                    }
                    .assinatura {
                        background-color: #e8f5e9;
                        padding: 15px;
                        border-radius: 5px;
                        margin-top: 20px;
                        font-style: italic;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h3>Agendamento de Aula - %s</h3>
                    </div>
                    
                    <p><strong>Olá, %s!</strong></p>
                    <p>Foi realizado um novo agendamento de aula associado a você.</p>
                    
                    <div class="section">
                        <div class="section-title">📚 DADOS DA DISCIPLINA</div>
                        <div class="info-grid">
                            <div class="label">Disciplina:</div>
                            <div class="value">%s</div>
                            
                            <div class="label">Professor:</div>
                            <div class="value">%s</div>
                            
                            <div class="label">Curso:</div>
                            <div class="value">%s</div>
                        </div>
                    </div>
                    
                    <div class="section">
                        <div class="section-title">📅 DADOS DO AGENDAMENTO</div>
                        <div class="info-grid">
                            <div class="label">PERÍODO:</div>
                            <div class="value">%s ---- %s</div>
                            <div class="label">DIA DE AULA:</div>
                            <div class="value">%s</div>
                        </div>

            
                        
                        <div class="label">HORÁRIOS:</div>
                        <div class="horarios-container">
                            %s
                        </div>

                    </div>
                    
                    <div class="assinatura">
                        <strong>Agendamento realizado por:</strong> %s
                    </div>
                    
                    <div class="footer">
                        <p>Esta é uma mensagem automática, por favor não responder.</p>
                        <p>Sistema GEA Fatec Itu • %s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                disciplina.getNome(),
                destinatario.getNome(),
                disciplina.getNome(),
                nomeProfessor,
                nomeCurso,
                dataInicial,
                dataFinal,
                diaDaSemana,
                horariosFormatados,
                nomeRemetente,
                LocalDate.now().getYear()
            );
    }

    @Async
    public void enviarNotificacaoCadastro(Usuario destinatario, String primeiraSenha) throws MessagingException {
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
        helper.setTo(destinatario.getEmail());
        helper.setFrom(hostEmail);
        helper.setSubject("Cadastro Realizado com Sucesso");

        String mensagem = montarMensagemCadastro(destinatario, destinatario.getLogin(), primeiraSenha);
        helper.setText(mensagem, true);
        mailSender.send(message);
      
    }

    private String montarMensagemCadastro(Usuario usuario, String login, String senhaInicial) {

        String nome = usuario != null ? usuario.getNome() : "Usuário";

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            line-height: 1.6;
                            color: #333;
                            margin: 0;
                            padding: 20px;
                            background-color: #f8f9fa;
                        }
                        .email-container {
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: white;
                            padding: 30px;
                            border-radius: 8px;
                            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        }
                        .header {
                            text-align: center;
                            padding-bottom: 20px;
                            border-bottom: 2px solid #2b7a0b;
                            margin-bottom: 25px;
                        }
                        .header h3 {
                            color: #2c3e50;
                            margin: 0;
                        }
                        .section-title {
                            background-color: #2b7a0b;
                            color: white;
                            padding: 10px 15px;
                            border-radius: 5px;
                            margin-bottom: 15px;
                            font-weight: bold;
                        }
                        .info-grid {
                            display: grid;
                            grid-template-columns: 130px 1fr;
                            gap: 10px;
                            margin-bottom: 10px;
                        }
                        .label {
                            font-weight: bold;
                            color: #555;
                        }
                        .value {
                            color: #333;
                        }
                        .alerta {
                            margin-top: 20px;
                            background-color: #fff3cd;
                            padding: 15px;
                            border-left: 4px solid #ffca2c;
                            border-radius: 5px;
                            font-weight: bold;
                            color: #856404;
                        }
                        .footer {
                            margin-top: 30px;
                            padding-top: 20px;
                            border-top: 1px solid #ddd;
                            font-size: 12px;
                            color: #777;
                            text-align: center;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="header">
                            <h3>Cadastro Realizado com Sucesso</h3>
                        </div>
                        
                        <p><strong>Olá, %s!</strong></p>
                        <p>Seu cadastro no sistema foi concluído com sucesso.</p>

                        <div class="section-title">🔐 DADOS DE ACESSO</div>
                        <div class="info-grid">
                            <div class="label">Login:</div>
                            <div class="value">%s</div>

                            <div class="label">Senha inicial:</div>
                            <div class="value">%s</div>
                        </div>

                        <div class="alerta">
                            ⚠️ Ao realizar seu primeiro login, não se esqueça de alterar sua senha!
                        </div>

                        <div class="footer">
                            <p>Esta é uma mensagem automática, por favor não responder.</p>
                            <p>Sistema GEA Fatec Itu • %s</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(
                    nome,
                    login,
                    senhaInicial,
                    LocalDate.now().getYear()
                );
        }


}

