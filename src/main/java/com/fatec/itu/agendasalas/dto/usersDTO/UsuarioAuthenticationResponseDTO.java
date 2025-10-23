package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAuthenticationResponseDTO(
        Long id,
        String nome,
        String cargoNome) {
    // private String token; -> usar com JWT.
}
