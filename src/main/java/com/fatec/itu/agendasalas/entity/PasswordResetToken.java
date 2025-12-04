package com.fatec.itu.agendasalas.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordResetToken {
    private static final int EXPIRATION = 15;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario usuario;
 
    private Date dataExpiracao;

    public PasswordResetToken(Usuario usuario, String token){
        this.token = token;
        this.usuario = usuario;
        this.dataExpiracao = Date.from(Instant.now().plus(EXPIRATION, ChronoUnit.MINUTES));
    }

}
