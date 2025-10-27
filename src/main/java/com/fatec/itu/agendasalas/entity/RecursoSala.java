package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RECURSOSSALAS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(RecursoSalaId.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecursoSala {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id_sala")
  private Long idSala;

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id_recurso")
  private Long idRecurso;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_sala", insertable = false, updatable = false)
  private Sala sala;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_recurso", insertable = false, updatable = false)
  private Recurso recurso;

  @Column(name = "quantidade")
  private Integer quantidade;
}
