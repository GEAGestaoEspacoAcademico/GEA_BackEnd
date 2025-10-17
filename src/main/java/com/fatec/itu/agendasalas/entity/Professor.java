package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROFESSORES")
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registroProfessor;
    private Usuario usuario;
    //private Disciplina[] Disciplinas;

    protected Professor() { }

    public Long getRegistroProfessor() {
        return registroProfessor;
    }    

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
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
