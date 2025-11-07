package com.fatec.itu.agendasalas.dto.notificações;

import java.util.List;

public record NotificacaoCreationDTO(
    Long agendamento, 
    String notificacaoTitulo, 
    String notificacaoMensagem, 
    Long usuarioRemetente, 
    List<Long> destinatarios
){}
