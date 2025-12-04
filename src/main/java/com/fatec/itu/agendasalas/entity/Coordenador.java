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
    private Long registroCoordenacao;
    
    public Coordenador() {
        super();
    }
    
    public Coordenador(String login, String email, String nome, Long registroCoordenacao){
        super(login, email, nome);
        this.registroCoordenacao = registroCoordenacao;
    }

    public Coordenador(Long id, String login, String email, String nome, Long registroCoordenacao){
        super(login, email, nome);
        this.registroCoordenacao = registroCoordenacao;
    }
    public Long getRegistroCoordenacao() {
        return registroCoordenacao;
    }

    public void setRegistroCoordenacao(Long registroCoordenacao) {
        this.registroCoordenacao = registroCoordenacao;
    }

}
