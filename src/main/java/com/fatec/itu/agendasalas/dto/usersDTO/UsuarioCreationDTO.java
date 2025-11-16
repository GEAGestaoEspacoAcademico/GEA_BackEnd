package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioCreationDTO(
        Long usuarioId,
        String usuarioNome,
        String usuarioEmail,
        String usuarioLogin,
        String usuarioSenha
){}
