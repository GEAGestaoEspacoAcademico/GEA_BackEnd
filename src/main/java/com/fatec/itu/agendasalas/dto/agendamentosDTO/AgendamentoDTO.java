package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDTO(
        String nomeUsuario,
        String nomeSala,
        LocalDate data,
        String diaDaSemana,
        LocalTime horaInicio,
        LocalTime horaFim,
        boolean isEvento
){}
