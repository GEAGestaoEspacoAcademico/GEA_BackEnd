package com.fatec.itu.agendasalas.dto.usersDTO;

public record InnerUsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Long cargoId) {
    // para consultar gets de admins, sem expor senhas e login
}
