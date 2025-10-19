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

  public RecursoSala(Recurso recurso, Sala sala, int quantidade) {
    this.sala = sala;
    this.recurso = recurso;
    this.quantidade = quantidade;
  }

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id_recurso", insertable = false, updatable = false)
  private Long idRecurso;

  @ManyToOne
  @JoinColumn(name = "id_sala")
  private Sala sala;

  @ManyToOne
  @JoinColumn(name = "id_recurso")
  private Recurso recurso;

  @Column(name = "quantidade", nullable = false)
  private int quantidade;
}
