package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(@NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull LocalDate data,
    @NotNull LocalTime horarioInicio,
    @NotNull LocalTime horarioFim,
    @NotBlank boolean isEvento
    ) {
}
