package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDTO(
        String nomeUsuario,
        // String nomeSala,
        LocalDate dataInicio,
        LocalDate dataFim,
        String diaDaSemana,
        LocalTime horaInicio,
        LocalTime horaFim,
        String tipo
){}
