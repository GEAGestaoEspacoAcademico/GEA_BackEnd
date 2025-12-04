package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRedefinirSenhaDTO(
    @NotBlank(message="O campo senha não pode ser vazio")
    String senha,

    @NotBlank(message="O campo repetir senha não pode ser vazio")
    String repetirSenha,

    @NotBlank(message="É necessário passar o token de validação de senha")
    String token
) {

}
