package com.fatec.itu.agendasalas.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="RECORRENCIA")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recorrencia {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "data_inicio", nullable=false)
    private LocalDate dataInicio;

    @Column(name="data_fim", nullable=false)
    private LocalDate dataFim;

    @OneToMany(mappedBy="recorrencia", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Agendamento> agendamentos;

    
    public void addAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
        agendamento.setRecorrencia(this);
    }

    public void removeAgendamento(Agendamento agendamento) {
        agendamentos.remove(agendamento);
        agendamento.setRecorrencia(null);
    }

    public Recorrencia(LocalDate dataInicio, LocalDate dataFim){
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.agendamentos = new ArrayList<>();
    }
    
}
