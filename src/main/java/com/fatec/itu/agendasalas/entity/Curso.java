package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "CURSOS")
@Entity
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    public Curso() {
    }

    public Curso(Long id, String nomeCurso, Coordenador coordenador) {
        this.id = id;
        this.nomeCurso = nomeCurso;
        this.coordenador = coordenador;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome_curso", nullable = false)
    private String nomeCurso;

    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private Coordenador coordenador;

    @OneToMany(mappedBy = "curso")
    private List<Disciplina> disciplinas;

    @Column(name = "sigla", length = 10, nullable = false)
    private String sigla;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void editarCurso(String novoNomeCurso, Coordenador novoCoordenador) {
        this.nomeCurso = novoNomeCurso;
        this.coordenador = novoCoordenador;
    }

    public void atribuirCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }
}
