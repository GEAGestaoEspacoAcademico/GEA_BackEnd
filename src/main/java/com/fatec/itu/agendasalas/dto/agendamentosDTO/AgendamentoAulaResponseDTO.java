package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoAulaResponseDTO {
    
    private Long id;
    private String nomeUsuario;
    private String nomeSala;
    private Integer disciplinaId;
    private String nomeDisciplina;
    private String nomeProfessor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String diaDaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String tipo;
}
