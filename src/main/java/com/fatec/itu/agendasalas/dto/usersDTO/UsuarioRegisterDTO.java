package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioRegisterDTO(
    @NotEmpty(message = "Nome é obrigatório")
    String usuarioNome,

    @NotEmpty(message = "E-mail é obrigatório")
    String usuarioEmail
) {}

