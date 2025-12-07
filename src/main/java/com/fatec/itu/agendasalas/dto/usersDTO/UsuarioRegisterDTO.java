package com.fatec.itu.agendasalas.dto.usersDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioRegisterDTO(
    @NotEmpty(message = "Nome é obrigatório")
    String usuarioNome,

    @NotEmpty(message = "E-mail é obrigatório")
    @EmailValido(message="Envie um e-mail válido")
    String usuarioEmail
) {}

