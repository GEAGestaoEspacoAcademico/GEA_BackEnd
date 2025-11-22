package com.fatec.itu.agendasalas.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="JANELAS_HORARIO")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JanelasHorario {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_inicio", nullable=false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable=false)
    private LocalTime horaFim;

    public JanelasHorario(LocalTime horaInicio, LocalTime horaFim) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
}
