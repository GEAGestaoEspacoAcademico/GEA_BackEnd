package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "DISCIPLINAS")
@Entity
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;

    public Disciplina() {
    }

    public Disciplina(String nome, String semestre, Curso curso) {
        this.nome = nome;
        this.semestre = semestre;
        this.curso = curso;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "semestre", nullable = false)
    private String semestre;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable=true)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void editarDisciplina(String novoNome, String novoSemestre, Professor novoProfessor) {
        this.nome = novoNome;
        this.semestre = novoSemestre;
        this.professor = novoProfessor;
    }

    public void atribuirProfessor(Professor professor) {
        this.professor = professor;
    }
}
