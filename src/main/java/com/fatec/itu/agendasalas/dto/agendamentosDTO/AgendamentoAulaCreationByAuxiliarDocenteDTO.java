package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;


public record AgendamentoAulaCreationByAuxiliarDocenteDTO(
    @NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull Long disciplinaId,
    @NotNull LocalDate data,
    @NotNull List<Long> janelasHorarioId, 
    @NotNull String solicitante
    
){}