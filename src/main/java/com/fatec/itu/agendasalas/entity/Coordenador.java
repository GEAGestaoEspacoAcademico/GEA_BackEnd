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
@Table(name = "COORDENADORES")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Coordenador extends Usuario {
    
    @EqualsAndHashCode.Include
    @Column(name = "registro_coordenacao", nullable = false, unique = true)
    private int registroCoordenacao;
    
    public Coordenador(String login, String email, String nome, int registroCoordenacao){
        super(login, email, nome);
        this.registroCoordenacao = registroCoordenacao;
    }
}