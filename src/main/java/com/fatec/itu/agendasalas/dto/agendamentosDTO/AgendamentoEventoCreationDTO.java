package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(@NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull LocalDate dataInicio,
    @NotNull LocalDate dataFim,
    @NotNull int quantidade,
    // @NotBlank boolean isEvento
    @NotNull Long janelasHorarioId
    ) {
}
