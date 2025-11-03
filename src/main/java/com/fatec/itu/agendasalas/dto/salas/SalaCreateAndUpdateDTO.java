package com.fatec.itu.agendasalas.dto.salas;

public record SalaCreateAndUpdateDTO(
        Long salaId,
        String salaNome, 
        int salaCapacidade, 
        int piso, 
        boolean disponibilidade,
        String salaObservacoes) {
}
