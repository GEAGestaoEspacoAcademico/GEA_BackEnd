package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AgendamentoEventoResponseDTO(
    Long agendamentoEventoId,
    String usuarioNome,
    String salaNome,
    String eventoNome,
    LocalDate data,
    String diaDaSemana,
    @JsonFormat(pattern = "HH:mm") LocalTime horaInicio,
    @JsonFormat(pattern = "HH:mm") LocalTime horaFim,
    boolean isEvento,
    Long recorrenciaId,
    String status,
    String solicitante

) {
    
}
