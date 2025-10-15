package com.fatec.itu.agendasalas.entity;

import java.util.List;

public class Aluno extends Usuario{
    private int ra;
    private List<AlunoDisciplina> disciplinas;
    
    public Aluno(String nome, String email, String senha, int ra, List<AlunoDisciplina> disciplinas){
        super(nome, email, senha);
        this.ra = ra;
        this.disciplinas = disciplinas;
    }

    public int getRa() {
        return ra;
    }

    public List<AlunoDisciplina> getDisciplinas() {
        return disciplinas;
    }

}
