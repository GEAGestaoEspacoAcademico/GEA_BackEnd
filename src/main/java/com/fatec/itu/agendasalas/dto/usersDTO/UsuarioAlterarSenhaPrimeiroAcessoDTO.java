package com.fatec.itu.agendasalas.dto.usersDTO;

public record UsuarioAlterarSenhaPrimeiroAcessoDTO(
    String usuarioLogin,
    String senhaAtual,
    String novaSenha,
    String repetirNovaSenha
) { }
