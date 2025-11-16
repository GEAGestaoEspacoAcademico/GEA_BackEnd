package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "AGENDAMENTO_EVENTOS")
@PrimaryKeyJoinColumn(name = "agendamento_id")
public class AgendamentoEvento extends Agendamento {
    
    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "id", nullable = false)
    private Evento evento;
}
