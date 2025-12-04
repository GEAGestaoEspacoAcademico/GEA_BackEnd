package com.fatec.itu.agendasalas.dto.secretariaDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SecretariaCreationDTO(
    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @NotNull(message = "Deverá informar a matricula do professor")
    Long matricula
  ) {
}
