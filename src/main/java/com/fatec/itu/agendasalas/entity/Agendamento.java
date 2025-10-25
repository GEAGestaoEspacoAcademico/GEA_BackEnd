package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "AGENDAMENTOS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Agendamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="sala_id", referencedColumnName="id", nullable = false)
    private Sala sala;

    @Column(name="data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name="data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name="dia_da_semana", nullable = false)
    private String diaDaSemana;

    @Column(name="hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name="hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

}
