package com.fatec.itu.agendasalas.dto.secretariaDTO;

public record SecretariaResponseDTO(
    Long usuarioId,
    String secretarioNome,
    String secretarioEmail,
    Long matricula,
    Long cargoId
){}
