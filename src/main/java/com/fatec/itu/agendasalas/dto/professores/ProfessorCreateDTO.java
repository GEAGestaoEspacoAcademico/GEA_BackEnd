package com.fatec.itu.agendasalas.dto.professores;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfessorCreateDTO(
    @NotEmpty(message = "Login é obrigatório")
    String login,

    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @NotEmpty(message = "Senha é obrigatória")
    String senha,

    @NotNull(message = "Deverá informar o registro de professor")
    Long registroProfessor
  ) {
}
