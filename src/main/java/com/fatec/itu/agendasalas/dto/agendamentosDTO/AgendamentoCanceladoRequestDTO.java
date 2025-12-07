package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoCanceladoRequestDTO(
    @NotNull(message="O usuario não pode ser nulo") 
    Long usuarioId,
    @NotBlank(message="O motivo não pode ser nulo")
    String motivoCancelamento
) {}
