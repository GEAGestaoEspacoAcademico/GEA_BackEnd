package com.fatec.itu.agendasalas.dto.salas;

public record SalaListDTO(
    Long salaId, 
    String salaNome, 
    int capacidade, 
    int piso, 
    boolean disponibilidade, 
    String tipoSala) {
}