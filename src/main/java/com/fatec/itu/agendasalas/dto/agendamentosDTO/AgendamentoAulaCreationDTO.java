package com.fatec.itu.agendasalas.dto.agendamentosDTO;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record AgendamentoAulaCreationDTO(
    @NotNull Long usuarioId,
    @NotNull Long salaId,   
    @NotNull Long disciplinaId,
    @NotNull int quantidade,
    @NotNull LocalDate data,
    @NotNull Long janelasHorarioId,
    @NotNull boolean isEvento
){}
