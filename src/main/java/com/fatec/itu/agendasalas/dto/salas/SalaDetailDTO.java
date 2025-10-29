package com.fatec.itu.agendasalas.dto.salas;

public record SalaDetailDTO(Long id, String nome, int capacidade, int piso, boolean disponibilidade,
    String tipoSala, String observacoes) {
}
