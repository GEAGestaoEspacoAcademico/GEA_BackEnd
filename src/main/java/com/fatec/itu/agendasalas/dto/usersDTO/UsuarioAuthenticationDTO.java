package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotBlank;

public record UsuarioAuthenticationDTO(
    @NotBlank(message = "Login é obrigatório") String usuarioLogin,
    @NotBlank(message = "Senha é obrigatória") String usuarioSenha

    ) {}
