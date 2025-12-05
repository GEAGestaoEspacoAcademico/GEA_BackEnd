package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Notificacao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacao implements Serializable {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacao;

    @ManyToOne
    @JoinColumn(name = "agendamento", nullable = false)
    private Agendamento agendamento;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "mensagem", nullable = false)
    private String mensagem;

    @Column(name = "data_envio", nullable = false)
    private LocalDate dataEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_remetente")
    private Usuario usuarioRemetente;

    @ManyToMany
    @JoinTable(
        name = "notificacao_destinatario",
        joinColumns = @JoinColumn(name = "notificacao_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> destinatarios = new ArrayList<>();

    public Notificacao(LocalDate dataEnvio) {
        this.destinatarios = new ArrayList<Usuario>();
        this.dataEnvio = dataEnvio == null ? LocalDate.now() : dataEnvio;
    }

    public void addDestinatario(Usuario usuario) {
        this.destinatarios.add(usuario);
    }
}