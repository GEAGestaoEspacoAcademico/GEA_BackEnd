package com.fatec.itu.agendasalas.entity;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROFESSORES")
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends Usuario {

    @Column(name = "registro_professor", nullable = false, unique = true)
    private Long registroProfessor;
    private ArrayList<Disciplina> Disciplinas;

    protected Professor() { }

    public Long getRegistroProfessor() {
        return registroProfessor;
    }
    
    public void setRegistroProfessor(Long registroProfessor) {
        this.registroProfessor = registroProfessor;
    }

    public void addDisciplina(Disciplina disciplina) {
        this.Disciplinas.add(disciplina);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((registroProfessor == null) ? 0 : registroProfessor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Professor other = (Professor) obj;
        if (registroProfessor == null) {
            if (other.registroProfessor != null)
                return false;
        } else if (!registroProfessor.equals(other.registroProfessor))
            return false;
        return true;
    }
}
