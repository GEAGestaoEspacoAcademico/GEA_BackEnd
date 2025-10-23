package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioCreationDTO(
        Long id,
        String nome,
        String email,
        String login,
        String senha) {
}
