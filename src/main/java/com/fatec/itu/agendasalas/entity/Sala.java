package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "SALAS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sala implements Serializable {

  private static final long serialVersionUID = 1L;

  public Sala(String nome, int capacidade, int piso, boolean isLaboratorio) {
    this.nome = nome;
    this.capacidade = capacidade;
    this.piso = piso;
    this.isLaboratorio = isLaboratorio;
    this.recursos = new ArrayList<>();
  }

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nome", nullable = false, unique = true)
  private String nome;

  @Column(name = "capacidade", nullable = false)
  private int capacidade;

  @Column(name = "piso", nullable = false)
  private int piso;

  @Column(name = "disponibilidade", nullable = false)
  private boolean disponibilidade;

  @OneToMany(mappedBy = "sala")
  private List<RecursoSala> recursos;

  @Column(name = "is_laboratorio", nullable = false)
  private boolean isLaboratorio;

  @Column(name = "observacoes")
  private String observacoes;
}
