package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

import com.fatec.itu.agendasalas.entity.DiaDaSemana;

import jakarta.validation.constraints.NotNull;

public record AgendamentoAulaCreationComRecorrenciaDTO(
    @NotNull
    Long usuarioId,    

    @NotNull
    LocalDate dataInicio,

    @NotNull
    LocalDate dataFim,

    @NotNull
    DiaDaSemana diaDaSemana,

    @NotNull
    List<Long> janelasHorarioId,

    @NotNull
    Long disciplinaId,

    @NotNull
    Long salaId
) {
    
}
