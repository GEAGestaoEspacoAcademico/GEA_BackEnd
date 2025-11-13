package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoAulaResponseDTO(
    Long agendamentoAulaId,
    String usuarioNome,
    String salaNome,
    Long disciplinaId,
    String disciplinaNome,
    String semestre,
    String cursoNome,
    String professorNome,
    LocalDate dataInicio,
    LocalDate dataFim,
    String diaDaSemana,
    LocalTime horaInicio,
    LocalTime horaFim
    // boolean isEvento
){}
