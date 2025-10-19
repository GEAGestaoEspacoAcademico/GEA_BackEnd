package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALUNOS")
@PrimaryKeyJoinColumn(name = "user_id")
public class Aluno extends Usuario{
    
    @Column(name = "ra", nullable = false, unique = true)
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

    public void setRa(int ra) {
        this.ra = ra;
    }

}
