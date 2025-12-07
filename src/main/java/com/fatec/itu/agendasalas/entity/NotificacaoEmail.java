package com.fatec.itu.agendasalas.entity;

import java.time.OffsetDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificacao_email")
public class NotificacaoEmail {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assunto")
    private String assunto;
    
    @Column(name="mensagem")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "remetente_id", referencedColumnName = "id", nullable = false)
    private Usuario remetente;

    private OffsetDateTime dia;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", referencedColumnName = "id", nullable = false)
    Usuario destinatario;

    
}   

