package com.fatec.itu.agendasalas.dto.notificações;

import java.util.List;

public record NotificacaoCreationDTO(
    Long notificacaoAgendamento, 
    String notificacaoTitulo, 
    String notificacaoMensagem, 
    Long usuarioRemetente, 
    List<Long> destinatarios
) { }
