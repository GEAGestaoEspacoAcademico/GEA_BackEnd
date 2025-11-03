package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAuthenticationResponseDTO(
        Long usuarioId,
        String usuarioNome,
        String usuarioCargo) {

    // private String token; -> usar com JWT.

}
