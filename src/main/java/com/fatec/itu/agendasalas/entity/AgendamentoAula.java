package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "AGENDAMENTO_AULAS")
@PrimaryKeyJoinColumn(name = "agendamento_id")
public class AgendamentoAula extends Agendamento {

    //@ManyToOne
    //@JoinColumn(name="disciplina_id", referencedColumnName="id", nullable = false)
    //private Disciplina disciplinaId;

}
