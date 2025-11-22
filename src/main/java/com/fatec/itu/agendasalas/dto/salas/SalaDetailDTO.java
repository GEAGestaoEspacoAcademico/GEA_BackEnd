package com.fatec.itu.agendasalas.dto.salas;

public record SalaDetailDTO(
    Long salaId,
    String salaNome,
    int capacidade,
    boolean disponibilidade,
    Long tipoSalaId,
    String tipoSala,
    Long andarId,
    String andarNome,
    String salaObservacoes
){ }
