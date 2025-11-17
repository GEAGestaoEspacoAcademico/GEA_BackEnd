package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.util.List;

public record DEVAgendamentoEventoDiasAgendadosDTO(
    LocalDate dia,
    List<Long> janelasHorarioId
) {   
}
