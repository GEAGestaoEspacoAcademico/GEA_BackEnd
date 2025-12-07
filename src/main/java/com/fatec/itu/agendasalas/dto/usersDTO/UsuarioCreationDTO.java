package com.fatec.itu.agendasalas.dto.usersDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;
import com.fatec.itu.agendasalas.validators.LoginValido;
import com.fatec.itu.agendasalas.validators.SenhaValida;

import jakarta.validation.constraints.NotEmpty;


public record UsuarioCreationDTO (
    @NotEmpty(message = "Login é obrigatório")
    @LoginValido
    String usuarioLogin,

    @NotEmpty(message = "Nome é obrigatório")
    String usuarioNome,

    @NotEmpty(message = "E-mail é obrigatório")
    @EmailValido(message="Envie um e-mail válido")
    String usuarioEmail,

    @NotEmpty(message = "Senha é obrigatória")
    @SenhaValida
    String usuarioSenha
) {}