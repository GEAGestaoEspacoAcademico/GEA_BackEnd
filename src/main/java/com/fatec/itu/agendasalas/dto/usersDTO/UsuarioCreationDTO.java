package com.fatec.itu.agendasalas.dto.usersDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;

import jakarta.validation.constraints.NotEmpty;


public record UsuarioCreationDTO (
    @NotEmpty(message = "Login é obrigatório")
    String usuarioLogin,

    @NotEmpty(message = "Nome é obrigatório")
    String usuarioNome,

    @NotEmpty(message = "E-mail é obrigatório")
    @EmailValido(message="Envie um e-mail válido")
    String usuarioEmail,

    @NotEmpty(message = "Senha é obrigatória")
    String usuarioSenha
) {}