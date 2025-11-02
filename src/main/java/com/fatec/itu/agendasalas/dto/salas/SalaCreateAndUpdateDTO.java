package com.fatec.itu.agendasalas.dto.salas;

public record SalaCreateAndUpdateDTO(String nome, int capacidade, int piso, boolean disponibilidade,
        Long idTipoSala, String observacoes) {
}
