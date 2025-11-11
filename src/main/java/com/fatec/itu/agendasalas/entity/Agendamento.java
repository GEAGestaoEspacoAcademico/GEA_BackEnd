package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.DayOfWeek;
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

    @ManyToOne
    @JoinColumn(name="janela_horario_id", referencedColumnName="id", nullable=false)
    private JanelasHorario janelasHorario;

    @Column(name = "tipo")
    private String tipo;

    @PrePersist
    @PreUpdate
    private void preencherDiaDaSemana() {
        if (this.dataInicio != null) {
            this.diaDaSemana = traduzirDiaDaSemana(this.dataInicio.getDayOfWeek());
        } else {
            this.diaDaSemana = null;
        }
    }

    private String traduzirDiaDaSemana(DayOfWeek day) {
        if (day == null) return null;
        switch (day) {
            case MONDAY:
                return "Segunda-feira";
            case TUESDAY:
                return "Terça-feira";
            case WEDNESDAY:
                return "Quarta-feira";
            case THURSDAY:
                return "Quinta-feira";
            case FRIDAY:
                return "Sexta-feira";
            case SATURDAY:
                return "Sábado";
            case SUNDAY:
                return "Domingo";
            default:
                return day.toString();
        }
    }

}