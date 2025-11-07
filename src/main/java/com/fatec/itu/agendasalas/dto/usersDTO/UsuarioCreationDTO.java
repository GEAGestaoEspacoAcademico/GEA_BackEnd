package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotEmpty;


public record UsuarioCreationDTO (
    @NotEmpty(message = "Login é obrigatório")
    String login,

    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @NotEmpty(message = "Senha é obrigatória")
    String senha
) {}
