package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "alunos")
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario{
    private int ra;
    
    public Aluno() {
        super();
    }
    
    public Aluno(String login, String email, String nome, int ra){
        super(login, email, nome);
        this.ra = ra;
    }

    public int getRa() {
        return ra;
    }


}
