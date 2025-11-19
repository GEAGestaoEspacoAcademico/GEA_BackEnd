package com.fatec.itu.agendasalas.entity;

import java.util.Collection;
import java.util.List;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name="USUARIOS")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails{

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

   @EqualsAndHashCode.Include


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        String cargoNome = this.cargo.getNome();

        return switch(cargoNome){
            case "USER" ->  List.of(new SimpleGrantedAuthority("ROLE_USER"));
            case "SECRETARIA" -> List.of(new SimpleGrantedAuthority("ROLE_SECRETARIA"));
            case "AUXILIAR_DOCENTE" -> List.of(new SimpleGrantedAuthority("ROLE_AUXILIAR_DOCENTE"), new SimpleGrantedAuthority("ROLE_USER"));
            case "COORDENADOR" -> List.of(new SimpleGrantedAuthority("ROLE_COORDENADOR"), new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_USER"));
            case "PROFESSOR" -> List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_USER"));
            default -> java.util.Collections.emptyList();

        };
       
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