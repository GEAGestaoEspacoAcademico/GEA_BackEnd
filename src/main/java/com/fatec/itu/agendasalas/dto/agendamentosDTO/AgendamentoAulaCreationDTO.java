package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;



public record AgendamentoAulaCreationDTO(
    @NotNull Long usuarioId,
    @NotNull String local,
    @NotNull String disciplina,
    @NotNull LocalDate data,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFim
    
){}