package com.fatec.itu.agendasalas.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USUARIOS")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails{

    private static final long serialVersionUID = 1L;

    public Usuario(String login, String email, String nome) {
        this.nome = nome;
        this.login = login;
        this.email = email;
    }

    @Id
    @EqualsAndHashCode.Include
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (cargo == null || cargo.getNome() == null) {
            return java.util.Collections.emptyList();
        }
        String nome = cargo.getNome().trim().toUpperCase();
       
        
        return java.util.Collections.singletonList(new SimpleGrantedAuthority(nome));
    }

    

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return true; 
    }
}