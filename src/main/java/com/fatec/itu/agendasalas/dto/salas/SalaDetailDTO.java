package com.fatec.itu.agendasalas.dto.salas;

public record SalaDetailDTO(
    Long salaId, 
    String salaNome, 
    int capacidade, 
    int piso, 
    boolean disponibilidade,
    Long tipoSalaId,
    String tipoSala, 
    String salaObservacoes
){ }
