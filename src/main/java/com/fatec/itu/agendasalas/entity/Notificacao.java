package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Notificacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Notificacao implements Serializable {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacao;

    
    @Column(name="agendamento_id", nullable=false)
    private Long agendamentoId;

    @Column(name="agendamento_data", nullable=false)
    private LocalDate data;

    @Column(name="agendamento_hora_inicio", nullable=false)
    private LocalTime horaInicio;

    @Column(name="agendamento_hora_fim", nullable=false)
    private LocalTime horaFim;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "mensagem", nullable = false)
    private String mensagem;

    @Column(name = "data_envio", nullable = false)
    private LocalDate dataEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_remetente")
    private Usuario usuarioRemetente;

    @ManyToOne
    private Usuario destinatario;

    public Notificacao(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio == null ? LocalDate.now() : dataEnvio;
    }


}