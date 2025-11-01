package com.fatec.itu.agendasalas.dto;

import java.time.LocalDate;
import java.util.List;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResumoDTO;

public record NotificacaoResponseDTO(
    Long idNotificacao,
    String titulo,
    String mensagem,
    LocalDate dataEnvio,
    UsuarioResumoDTO usuarioRemetente,
    List<UsuarioResumoDTO> destinatarios,
    String canalEnvio,
    AgendamentoNotificacaoDTO agendamento
) { }