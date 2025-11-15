package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

public record AgendamentoEventoDiasAgendadosDTO(
    LocalDate dia,
    List<Long> janelasHorarioId
) {   
}
