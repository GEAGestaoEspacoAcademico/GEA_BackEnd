package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecursoSalaId implements Serializable {
  @EqualsAndHashCode.Include
  @Column(name = "recurso_id")
  private Long recursoId;

  @EqualsAndHashCode.Include
  @Column(name = "sala_id")
  private Long salaId;
}
