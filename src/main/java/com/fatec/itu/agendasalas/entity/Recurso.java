package com.fatec.itu.agendasalas.entity;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "RECURSOS")
@Entity
@Getter
@Setter
public class Recurso implements Serializable {
  private static final long serialVersionUID = 1L;

  public Recurso(Long id, String nome, String tipo) {
    this.id = id;
    this.tipo = tipo;
    this.nome = nome;
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
}
