package com.fatec.itu.agendasalas.entity; 

import java.io.Serializable;
import java.util.ArrayList;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CURSOS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
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
    private List<Disciplina> disciplinas = new ArrayList<>(); 

    @Column(name = "sigla", length = 10, nullable = false)
    private String sigla;

    public void editarCurso(String novoNomeCurso, Coordenador novoCoordenador) {
        this.nomeCurso = novoNomeCurso;
        this.coordenador = novoCoordenador;
    }

    public void atribuirCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }
}