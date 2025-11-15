package com.fatec.itu.agendasalas.dto.agendamentosDTO;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record AgendamentoAulaCreationDTO(



    @NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull Long disciplinaId,
    @NotNull LocalDate data,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFim
    
)
{}
