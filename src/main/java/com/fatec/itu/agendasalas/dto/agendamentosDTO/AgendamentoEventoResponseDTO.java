package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoEventoResponseDTO(
    Long agendamentoEventoId,
    String usuarioNome,
    String salaNome,
    String eventoNome,
    LocalDate data,
    String diaDaSemana,
    LocalTime horaInicio,
    LocalTime horaFim,
    boolean isEvento,
    Long recorrenciaId,
    String status,
    String solicitante

) {
    
}
