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

    public Disciplina(String nome, int semestre, Professor professor) {
        this.nome = nome;
        this.semestre = semestre;
        this.professor = professor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "semestre", nullable = false)
    private int semestre;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
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

    public void editarDisciplina(String novoNome, int novoSemestre, Professor novoProfessor) {
        this.nome = novoNome;
        this.semestre = novoSemestre;
        this.professor = novoProfessor;
    }

    public void atribuirProfessor(Professor professor) {
        this.professor = professor;
    }
}
