package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoDiasAgendadosDTO(
    @NotNull LocalDate dia,
    @NotNull @JsonFormat(pattern = "HH:mm") LocalTime horaInicio,
    @NotNull @JsonFormat(pattern = "HH:mm") LocalTime horaFim
) {
    
}
