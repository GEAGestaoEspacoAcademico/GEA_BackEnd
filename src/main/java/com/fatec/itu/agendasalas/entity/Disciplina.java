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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "DISCIPLINAS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    @Column(name = "excluida", nullable = false)
    private boolean excluida = false;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable=true)
    private Usuario professor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Disciplina(String nome, Semestre semestre, Curso curso) {
        this.nome = nome;
        this.semestre = semestre;
        this.curso = curso;
    }

    public void editarDisciplina(String novoNome, Semestre novoSemestre, Professor novoProfessor) {
        this.nome = novoNome;
        this.semestre = novoSemestre;
        this.professor = novoProfessor;
    }

    public void atribuirProfessor(Professor professor) {
        this.professor = professor;
    }
}