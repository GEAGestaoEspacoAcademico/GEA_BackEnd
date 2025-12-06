package com.fatec.itu.agendasalas.dto.agendamentosDTO;

public record AgendamentoCanceladoRequestDTO(
    Long usuarioId,
    String motivoCancelamento
) {}
