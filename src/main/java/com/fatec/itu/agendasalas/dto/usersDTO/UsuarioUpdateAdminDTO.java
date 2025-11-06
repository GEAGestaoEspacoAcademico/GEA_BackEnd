package com.fatec.itu.agendasalas.dto.usersDTO;

//aqui somente admins poderão usar, pois não queremos que um usuario mude seu cargo.
public record UsuarioUpdateAdminDTO(
        String usuarioNome,
        String usuarioEmail,
        Long cargoId
){}
