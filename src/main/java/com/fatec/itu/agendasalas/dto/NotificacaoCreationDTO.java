package com.fatec.itu.agendasalas.dto;

import java.util.List;

public record NotificacaoCreationDTO(
    Long agendamento, 
    String titulo, 
    String mensagem, 
    Long usuarioRemetente, 
    List<Long> destinatarios, 
    String canalEnvio
) { }
