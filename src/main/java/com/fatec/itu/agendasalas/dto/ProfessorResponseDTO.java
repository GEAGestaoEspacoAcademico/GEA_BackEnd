package com.fatec.itu.agendasalas.dto;

public record ProfessorResponseDTO(
    Long id,
    String nome,
    String email,
    Long registroProfessor,
    Long cargoId
  ) { }
