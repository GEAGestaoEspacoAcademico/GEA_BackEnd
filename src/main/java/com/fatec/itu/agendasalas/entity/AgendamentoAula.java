package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "AGENDAMENTO_AULAS")
@PrimaryKeyJoinColumn(name = "agendamento_id")
@EqualsAndHashCode(callSuper = true)
public class AgendamentoAula extends Agendamento {

    @ManyToOne
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id", nullable = false)
    private Disciplina disciplina;
}