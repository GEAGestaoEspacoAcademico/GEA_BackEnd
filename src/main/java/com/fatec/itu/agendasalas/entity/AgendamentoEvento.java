package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "AGENDAMENTO_AULAS")
@DiscriminatorValue("AULA")
@PrimaryKeyJoinColumn(name = "agendamento_id")
public class AgendamentoEvento extends Agendamento {
    
}
