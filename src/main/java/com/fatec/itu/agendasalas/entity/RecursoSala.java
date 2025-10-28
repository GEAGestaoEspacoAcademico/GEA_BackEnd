package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "RECURSOSSALAS")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class RecursoSala implements Serializable {
  private static final long serialVersionUID = 1L;

  // Este primeiro construtor era o que estava sendo usado antes da alteração para
  // classes record... mantive no código para evitar erros em
  // quaisquer códigos que passem ids como atributos
  public RecursoSala(Long idRecurso, Long idSala, Integer quantidade) {
    this.idRecurso = idRecurso;
    this.idSala = idSala;
    this.quantidade = quantidade;
  }

  public RecursoSala(Recurso recurso, Sala sala, Integer quantidade) {
    this.recurso = recurso;
    this.sala = sala;
    this.idRecurso = recurso != null ? recurso.getId() : null;
    this.idSala = sala != null ? sala.getId() : null;
    this.quantidade = quantidade;
  }

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id_recurso", insertable = false, updatable = false)
  private Long idRecurso;

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id_sala", insertable = false, updatable = false)
  private Long idSala;

  @ManyToOne
  @JoinColumn(name = "id_sala")
  private Sala sala;

  @ManyToOne
  @JoinColumn(name = "id_recurso")
  private Recurso recurso;

  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;
}
