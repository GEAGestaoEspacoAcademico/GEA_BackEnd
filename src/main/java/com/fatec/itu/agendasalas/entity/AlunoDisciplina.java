package com.fatec.itu.agendasalas.entity;

public class AlunoDisciplina {
    private Aluno aluno;
    private Disciplina disciplina;

    public AlunoDisciplina(Aluno aluno, Disciplina disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public Aluno getAluno(){ 
        return aluno;
     }

    public Disciplina getDisciplina(){ 
        return disciplina;
    }

}
    