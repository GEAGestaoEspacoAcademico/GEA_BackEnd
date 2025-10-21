package com.fatec.itu.agendasalas.entity;
//
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="USUARIOS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable{

    private static final long serialVersionUID = 1L;

    public Usuario(String login, String email, String nome) {
        this.nome = nome;
        this.login = login;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="senha", nullable = false)
    private String senha;

    @Column(name="email", nullable = false, unique=true)
    private String email;

    @Column(name="login", nullable = false, unique=true)
    private String login;

    @Column(name="nome", nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name="cargo_id", referencedColumnName = "id")
    private Cargo cargo;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Usuario other = (Usuario) obj;
        return id != null && id.equals(other.id);
    }
}
