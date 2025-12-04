package com.fatec.itu.agendasalas.dto.secretariaDTO;

import jakarta.validation.constraints.Email;

public record SecretariaUpdateDTO(
    String nome,
    @Email(message = "Email inválido")
    String email,
    Long matricula
) {}