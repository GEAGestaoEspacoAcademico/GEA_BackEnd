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

@Entity
@Table(name = "Notificacao")
public class Notificacao implements Serializable {
    
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


    public Notificacao() {

        destinatarios = new ArrayList<Usuario>();
    }

    public Notificacao(LocalDate dataEnvio) {
        this.destinatarios = new ArrayList<Usuario>();
        this.dataEnvio = dataEnvio == null ? LocalDate.now() : dataEnvio;
    }

    public void setIdNotificacao(Long idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public void setUsuarioRemetente(Usuario usuarioRemetente) {
        this.usuarioRemetente = usuarioRemetente;
    }

    public void setDestinatario(List<Usuario> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Long getIdNotificacao() {
        return idNotificacao;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public Usuario getUsuarioRemetente() {
        return usuarioRemetente;
    }

    public List<Usuario> getDestinatario() {
        return destinatarios;
    }

    public void addDestinatario(Usuario usuario) {
        this.destinatarios.add(usuario);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idNotificacao == null) ? 0 : idNotificacao.hashCode());
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
        Notificacao other = (Notificacao) obj;
        if (idNotificacao == null) {
            if (other.idNotificacao != null)
                return false;
        } else if (!idNotificacao.equals(other.idNotificacao))
            return false;
        return true;
    }   
}
