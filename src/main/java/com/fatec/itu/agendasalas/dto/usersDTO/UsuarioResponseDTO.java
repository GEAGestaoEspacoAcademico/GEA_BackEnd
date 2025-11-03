package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Long cargoId
) {
}
