package com.fatec.itu.agendasalas.dto.salas;

public record SalaCreateAndUpdateDTO(
        Long tipoSalaId,
        Long andarId,
        String salaNome, 
        Integer salaCapacidade, 
        Boolean disponibilidade,
        String salaObservacoes
){ }
