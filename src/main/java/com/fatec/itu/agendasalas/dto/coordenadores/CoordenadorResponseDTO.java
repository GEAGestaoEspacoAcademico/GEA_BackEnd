package com.fatec.itu.agendasalas.dto.coordenadores;

public record CoordenadorResponseDTO(
        Long id,
        String nome,
        String email,
        int registroCoordenacao,
        Long cargoId) {
}
