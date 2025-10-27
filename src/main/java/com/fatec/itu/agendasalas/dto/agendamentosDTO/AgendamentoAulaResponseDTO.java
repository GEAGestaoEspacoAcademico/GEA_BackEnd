package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoAulaResponseDTO(
    Long id,
    String nomeUsuario,
    String nomeSala,
    Integer disciplinaId,
    String nomeDisciplina,
    String semestre,
    String curso,
    String nomeProfessor,
    LocalDate dataInicio,
    LocalDate dataFim,
    String diaDaSemana,
    LocalTime horaInicio,
    LocalTime horaFim,
    String tipo) {
    
   
}
