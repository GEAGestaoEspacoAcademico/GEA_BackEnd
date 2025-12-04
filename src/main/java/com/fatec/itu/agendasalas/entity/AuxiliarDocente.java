package com.fatec.itu.agendasalas.entity; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "AUXILIAR_DOCENTES")
@PrimaryKeyJoinColumn(name = "user_id")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AuxiliarDocente extends Usuario {
    
    @Column(name = "area", nullable=false)
    private String area;

    public AuxiliarDocente(String login, String email, String nome, String area) {
        super(login, email, nome);
        this.area = area; 
    }
}