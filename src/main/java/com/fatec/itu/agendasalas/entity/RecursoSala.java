package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RECURSOS_SALAS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecursoSala {

  @EqualsAndHashCode.Include
  @EmbeddedId
  private RecursoSalaId id;

  @ManyToOne(optional = false)
  @MapsId("salaId")
  @JoinColumn(name = "sala_id")
  private Sala sala;

  @ManyToOne(optional = false)
  @MapsId("recursoId")
  @JoinColumn(name = "recurso_id")
  private Recurso recurso;

  @Column(name = "quantidade")
  private Integer quantidade;
}
