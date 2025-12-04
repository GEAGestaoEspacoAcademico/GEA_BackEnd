package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoAulaResponseDTO(
    Long agendamentoAulaId,
    String usuarioNome,
    Long salaId,
    String salaNome,
    Long disciplinaId,
    String disciplinaNome,
    String semestre,
    String cursoNome,
    String professorNome,
    LocalDate data,
    String diaDaSemana,
    Long janelaHorarioId,
    LocalTime horaInicio,
    LocalTime horaFim,
    boolean isEvento
){}
