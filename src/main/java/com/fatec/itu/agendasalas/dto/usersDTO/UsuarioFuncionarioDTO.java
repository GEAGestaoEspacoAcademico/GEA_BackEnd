package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioFuncionarioDTO(
    Long usuarioId,
    String usuarioNome,
    String usuarioEmail,
    Long registro,
    Long cargoId,
    String cargoNome
){ }
