package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "RECURSOS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recurso implements Serializable {
  private static final long serialVersionUID = 1L;

  public Recurso(String nome, TipoRecurso tipoRecurso) {
    this.nome = nome;
    this.tipoRecurso = tipoRecurso;
  }

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @ManyToOne
  @JoinColumn(name = "tipo_recurso_id")
  private TipoRecurso tipoRecurso;

  @OneToMany(mappedBy = "recurso", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<RecursoSala> salas;

}
