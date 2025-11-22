package com.fatec.itu.agendasalas.dto.salas;

public record SalaListDTO(
    Long salaId,
    String salaNome,
    int capacidade,
    boolean disponibilidade,
    String tipoSala,
    Long andarId,
    String andarNome
){}