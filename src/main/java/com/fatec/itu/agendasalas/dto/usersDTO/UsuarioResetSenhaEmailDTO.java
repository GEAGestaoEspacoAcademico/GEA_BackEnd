package com.fatec.itu.agendasalas.dto.usersDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;

import jakarta.validation.constraints.NotBlank;

public record UsuarioResetSenhaEmailDTO(
    @EmailValido(message="Envie um e-mail válido")
    @NotBlank(message = "Precisa informar um e-mail")
    String email
) {

}
