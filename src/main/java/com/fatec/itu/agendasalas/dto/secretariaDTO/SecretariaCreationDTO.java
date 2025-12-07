package com.fatec.itu.agendasalas.dto.secretariaDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;
import com.fatec.itu.agendasalas.validators.LoginValido;
import com.fatec.itu.agendasalas.validators.SenhaValida;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SecretariaCreationDTO(
    @NotEmpty(message = "E-mail é obrigatório")
    @EmailValido(message="Envie um e-mail válido")
    String email,
  
    @NotEmpty(message="Login é obrigatório")
    @LoginValido
    String login,   

    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @NotNull(message = "Deverá informar a matricula do professor")
    Long matricula,

    @SenhaValida
    String senha

  ) {
}
