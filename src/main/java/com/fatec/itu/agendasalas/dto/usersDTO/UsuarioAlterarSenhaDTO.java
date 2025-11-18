package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAlterarSenhaDTO(
    String senhaAtual,
    String novaSenha,
    String repetirNovaSenha
) {}
