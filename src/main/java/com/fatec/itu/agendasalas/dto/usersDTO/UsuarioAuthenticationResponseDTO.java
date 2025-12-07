package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAuthenticationResponseDTO(
        Long usuarioId,
        String usuarioNome,
        String usuarioCargo,
        boolean novoUsuario
){   // private String token; -> usar com JWT.
}
