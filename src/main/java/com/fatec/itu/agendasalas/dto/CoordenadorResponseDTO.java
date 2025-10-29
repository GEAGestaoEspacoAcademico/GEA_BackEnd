package com.fatec.itu.agendasalas.dto;

public record CoordenadorResponseDTO(
        Long id,
        String nome,
        String email,
        int registroCoordenacao,
        Long cargoId) {
}
