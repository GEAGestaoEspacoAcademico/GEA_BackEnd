package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

import com.fatec.itu.agendasalas.validators.EmailValido;
import com.fatec.itu.agendasalas.validators.LoginValido;
import com.fatec.itu.agendasalas.validators.SenhaValida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfessorCreateDTO(
    @NotEmpty(message = "A lista de disciplinas não pode ser vazia")
    List<Long> disciplinasId,

    @EmailValido(message="Envie um e-mail válido")
    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @LoginValido
    String login,

    @NotBlank(message = "Nome é obrigatório")
    String nome,
    
    @NotNull(message = "Deverá informar a matricula de professor")
    Long matricula,

    @SenhaValida
    String senha

    
  ) {
}
