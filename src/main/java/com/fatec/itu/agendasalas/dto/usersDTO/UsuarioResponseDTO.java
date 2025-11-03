package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioResponseDTO(
        Long usuarioId,
        String usuarioNome,
        String usuarioEmail,
        Long cargoId
) {
}
