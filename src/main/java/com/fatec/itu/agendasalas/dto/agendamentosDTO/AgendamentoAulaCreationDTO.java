package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgendamentoAulaCreationDTO {
    
    private Long usuarioId;
    private Long salaId;
    private Integer disciplinaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String diaDaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String tipo;

    public AgendamentoAulaCreationDTO(Long usuarioId, Long salaId, Integer disciplinaId, 
                                       LocalDate dataInicio, LocalDate dataFim, String diaDaSemana,
                                       LocalTime horaInicio, LocalTime horaFim, String tipo) {
        this.usuarioId = usuarioId;
        this.salaId = salaId;
        this.disciplinaId = disciplinaId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diaDaSemana = diaDaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tipo = tipo;
    }
}
