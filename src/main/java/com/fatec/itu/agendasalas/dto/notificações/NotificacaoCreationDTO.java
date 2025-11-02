package com.fatec.itu.agendasalas.dto.notificações;

import java.util.List;

public record NotificacaoCreationDTO(
    Long agendamento, 
    String titulo, 
    String mensagem, 
    Long usuarioRemetente, 
    List<Long> destinatarios
) { }
