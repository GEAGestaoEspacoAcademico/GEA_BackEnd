package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoExclusaoDTO(
    @NotNull
    Long agendamentoEventoId

) {}
