package com.fatec.itu.agendasalas.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AGENDAMENTOS_CANCELADOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoCancelado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long agendamentoOriginalId;
    private String tipoAgendamento; 
    private LocalDate data; 
    private String statusOriginal;
    private String solicitante;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "usuario_id") 
    private Usuario usuario; 
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "cancelado_por_id") 
    private Usuario canceladoPor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "janelas_horario_id")
    private JanelasHorario janelasHorario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorrencia_id")
    private Recorrencia recorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private String motivoCancelamento;
    private LocalDateTime dataHoraCancelamento;
}