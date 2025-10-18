package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "COORDENADORES")
@PrimaryKeyJoinColumn(name = "user_id")
public class Coordenador extends Usuario {
    
    @Column(name = "registro_coordenacao", nullable = false, unique = true)
    private int registroCoordenacao;
    
    public Coordenador() {
        super();
    }
    
    public Coordenador(String login, String email, String nome, int registroCoordenacao){
        super(login, email, nome);
        this.registroCoordenacao = registroCoordenacao;
    }

    public int getRegistroCoordenacao() {
        return registroCoordenacao;
    }

    public void setRegistroCoordenacao(int registroCoordenacao) {
        this.registroCoordenacao = registroCoordenacao;
    }

}
