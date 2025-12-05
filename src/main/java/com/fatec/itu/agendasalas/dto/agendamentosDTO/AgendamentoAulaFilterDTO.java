package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

public record AgendamentoAulaFilterDTO(
    List<Long> usuarioIds,
    List<Long> salaIds,
    List<Long> disciplinaIds,
    LocalDate dataInicio,
    LocalDate dataFim,
    Boolean isEvento,
    List<Long> janelaHorarioIds
) {}
