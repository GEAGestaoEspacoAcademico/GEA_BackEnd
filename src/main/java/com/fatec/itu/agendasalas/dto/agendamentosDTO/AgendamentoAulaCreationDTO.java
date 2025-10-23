package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoAulaCreationDTO(
        Long usuarioId,
        Long salaId,
        Integer disciplinaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        String diaDaSemana,
        LocalTime horaInicio,
        LocalTime horaFim,
        String tipo) {
}
