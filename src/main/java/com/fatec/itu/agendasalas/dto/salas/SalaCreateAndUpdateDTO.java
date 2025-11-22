package com.fatec.itu.agendasalas.dto.salas;

public record SalaCreateAndUpdateDTO(
        Long tipoSalaId,
        String salaNome, 
        Integer salaCapacidade, 
        Integer piso, 
        Boolean disponibilidade,
        String salaObservacoes
){ }
