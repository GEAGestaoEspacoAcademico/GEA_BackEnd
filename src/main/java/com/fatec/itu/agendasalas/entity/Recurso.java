package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "RECURSOS")
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recurso implements Serializable {
  private static final long serialVersionUID = 1L;

  public Recurso(String nome, String tipo) {
    this.nome = nome;
    this.tipo = tipo;
  }

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "tipo", nullable = false)
  private String tipo;

  @OneToMany(mappedBy = "recurso", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<RecursoSala> salas;
}
