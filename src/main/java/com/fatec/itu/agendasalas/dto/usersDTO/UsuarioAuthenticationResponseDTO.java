package com.fatec.itu.agendasalas.dto.usersDTO;

<<<<<<< HEAD
public record UsuarioAuthenticationResponseDTO(String token){}
=======
public record UsuarioAuthenticationResponseDTO(
        Long usuarioId,
        String usuarioNome,
        String usuarioCargo
){   // private String token; -> usar com JWT.
}
>>>>>>> 22ba608b6cb8f0d73d70e4d20954c5e09917835e
