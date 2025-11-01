package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAuthenticationResponseDTO(
        Long id,
        String nome,
        String cargo) {

    // private String token; -> usar com JWT.

}
