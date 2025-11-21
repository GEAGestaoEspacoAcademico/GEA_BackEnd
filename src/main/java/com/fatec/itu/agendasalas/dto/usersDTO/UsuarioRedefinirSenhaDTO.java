package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRedefinirSenhaDTO(
    @NotBlank(message = "A nova senha é obrigatória.")
    String novaSenha
){} 
