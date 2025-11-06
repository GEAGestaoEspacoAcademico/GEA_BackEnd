package com.fatec.itu.agendasalas.dto.coordenadores;

public record CoordenadorResponseDTO(
        Long coordenadorUsuarioId,
        String coordenadorNome,
        String coordenadorEmail,
        int registroCoordenacao,
        Long cargoId
){}
