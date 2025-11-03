package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter


@Entity
@Table(name = "AUXILIAR_DOCENTES")
@PrimaryKeyJoinColumn(name = "user_id")
public class AuxiliarDocente extends Usuario {
    
    @Column(name = "area", nullable=false)
    private String area;

}
