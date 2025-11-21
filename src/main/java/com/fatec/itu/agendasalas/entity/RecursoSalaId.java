package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecursoSalaId implements Serializable {
  @EqualsAndHashCode.Include
  @Column(name = "id_recurso")
  private Long idRecurso;

  @EqualsAndHashCode.Include
  @Column(name = "id_sala")
  private Long idSala;
}
