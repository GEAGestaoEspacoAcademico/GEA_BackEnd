package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

import com.fatec.itu.agendasalas.validators.NotEmptyList;

public record AgendamentoAulaFilterDTO(
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> usuarioIds,

    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> salaIds,

    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> disciplinaIds,

    LocalDate dataInicio,

    LocalDate dataFim,
    
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> janelaHorarioIds
) {}
