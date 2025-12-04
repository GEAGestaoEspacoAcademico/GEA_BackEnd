package com.fatec.itu.agendasalas.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROFESSORES")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Professor extends Usuario {

    @EqualsAndHashCode.Include
    @Column(name = "registro_professor", nullable = false, unique = true)
    private Long registroProfessor;

    @OneToMany(mappedBy = "professor")
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Professor(String login, String email, String nome, Long registroProfessor) {
        super(login, email, nome);
        this.registroProfessor = registroProfessor;
    }

    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }
}