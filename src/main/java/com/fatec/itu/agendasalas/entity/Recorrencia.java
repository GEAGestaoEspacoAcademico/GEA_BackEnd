package com.fatec.itu.agendasalas.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="RECORRENCIA")
public class Recorrencia {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "data_inicio", nullable=false)
    private LocalDate dataInicio;

    @Column(name="data_fim", nullable=false)
    private LocalDate dataFim;

    public Recorrencia(LocalDate dataInicio, LocalDate dataFim){
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    
}
