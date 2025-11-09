package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotEmpty;


public record UsuarioCreationDTO (
    @NotEmpty(message = "Login é obrigatório")
    String usuarioLogin,

    @NotEmpty(message = "Nome é obrigatório")
    String usuarioNome,

    @NotEmpty(message = "E-mail é obrigatório")
    String usuarioEmail,

    @NotEmpty(message = "Senha é obrigatória")
    String usuarioSenha
) {}