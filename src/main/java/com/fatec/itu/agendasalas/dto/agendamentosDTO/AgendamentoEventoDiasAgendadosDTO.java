package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoDiasAgendadosDTO(
    @NotNull LocalDate dia,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFim
) {
    
}
