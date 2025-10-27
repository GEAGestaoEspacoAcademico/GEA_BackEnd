package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record AgendamentoAulaCreationDTO (
    @NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull Long disciplinaId,
    @NotNull LocalDate dataInicio,
    @NotNull LocalDate dataFim,
    @NotBlank String diaDaSemana,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFim,
    @NotBlank String tipo){}


    


