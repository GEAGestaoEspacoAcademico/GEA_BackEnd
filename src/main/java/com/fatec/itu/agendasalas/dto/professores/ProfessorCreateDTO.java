package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfessorCreateDTO(

    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @Email(message="Envie um e-mail válido", regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")
    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @NotNull(message = "Deverá informar o registro de professor")
    Long registroProfessor,

    @NotEmpty(message = "A lista de disciplinas não pode ser vazia")
    List<Long> disciplinasId

    
  ) {
}
