package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
public record ProfessorAdicionandoDisciplinaDTO(   
   @NotEmpty(message = "A lista de ids de disciplina não pode ser vazia")
   List<Long> disciplinaId
  ) {
}
