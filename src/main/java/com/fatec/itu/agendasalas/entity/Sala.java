package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import lombok.Builder;
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
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sala implements Serializable {
  private static final long serialVersionUID = 1L;

  public Sala(String nome, int capacidade, int piso, TipoSala tipoSala) {
    this.nome = nome;
    this.capacidade = capacidade;
    this.piso = piso;
    this.tipoSala = tipoSala;
    recursos = new ArrayList<>();
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

  @OneToMany(mappedBy = "sala", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<RecursoSala> recursos;

  @OneToMany(mappedBy = "sala", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Agendamento> agendamentos;

  @ManyToOne
  @JoinColumn(name = "id_tipo_sala")
  private TipoSala tipoSala;

  @Column(name = "observacoes")
  private String observacoes;
}
