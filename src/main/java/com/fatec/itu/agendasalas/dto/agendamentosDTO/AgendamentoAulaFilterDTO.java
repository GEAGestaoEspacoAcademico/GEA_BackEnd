package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

import com.fatec.itu.agendasalas.validators.NotEmptyList;

import io.swagger.v3.oas.annotations.media.Schema;

public record AgendamentoAulaFilterDTO(
    @Schema(
        description = "Lista de IDs de usuários para filtrar.",
        example = "[1, 2]"
    )
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> usuarioIds,

    @Schema(
        description = "Lista de IDs das salas.",
        example = "[1, 5]"
    )
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> salaIds,

    @Schema(
        description = "Lista de IDs das disciplinas.",
        example = "[1, 4]"
    )
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> disciplinaIds,

    @Schema(
        description = "Data final (formato ISO: yyyy-MM-dd).",
        example = "2025-12-16"
    )
    LocalDate dataInicio,

    @Schema(
        description = "Lista de IDs das janelas de horário.",
        example = "[1, 3, 6]"
    )
    LocalDate dataFim,
    
    @NotEmptyList(message = "A lista de IDs nao pode ser vazia.")
    List<Long> janelaHorarioIds
) {}
