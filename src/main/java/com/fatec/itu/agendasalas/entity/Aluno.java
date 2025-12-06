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

@Entity
@Table(name = "ALUNOS")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Aluno extends Usuario {
    
    @EqualsAndHashCode.Include
    @Column(name = "ra", nullable = false, unique = true)
    private int ra;
    
    public Aluno(String login, String email, String nome, int ra){
        super(login, email, nome);
        this.ra = ra;
    }
}