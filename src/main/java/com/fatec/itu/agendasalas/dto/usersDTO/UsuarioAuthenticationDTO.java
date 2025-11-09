package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioAuthenticationDTO(
    @NotEmpty(message = "Login é obrigatório") String usuarioLogin,
    @NotEmpty(message = "Senha é obrigatória") String usuarioSenha

    ) {}
