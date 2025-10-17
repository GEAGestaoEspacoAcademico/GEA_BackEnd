package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgendamentoDTO {
 

        private String nomeUsuario;
        //private String nomeSala;
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private String diaDaSemana;
        private LocalTime horaInicio;
        private LocalTime horaFim;   
        private String tipo;

        public AgendamentoDTO(String nomeUsuario, LocalDate dataInicio, LocalDate dataFim, String diaDaSemana, LocalTime horaInicio, LocalTime horaFim, String tipo){
            this.nomeUsuario = nomeUsuario;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.diaDaSemana = diaDaSemana;
            this.horaInicio = horaInicio;
            this.horaFim = horaFim;
            this.tipo = tipo;
        }
    
    }
    


