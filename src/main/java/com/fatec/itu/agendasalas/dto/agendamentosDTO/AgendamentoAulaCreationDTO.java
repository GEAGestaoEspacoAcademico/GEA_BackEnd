package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record AgendamentoAulaCreationDTO(
    @NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull Long disciplinaId,
    @NotNull LocalDate dataInicio,
    @NotNull LocalDate dataFim,
    @NotNull Long janelasHorarioId,
    @NotBlank boolean isEvento
){}