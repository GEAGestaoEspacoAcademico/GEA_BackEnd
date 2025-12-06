package com.fatec.itu.agendasalas.dto.secretariaDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;

public record SecretariaUpdateDTO(
    String nome,
    @EmailValido(message="Envie um e-mail válido")
    String email,
    Long matricula
) {}