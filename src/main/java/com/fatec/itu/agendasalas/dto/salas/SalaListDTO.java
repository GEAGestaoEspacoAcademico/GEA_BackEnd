package com.fatec.itu.agendasalas.dto.salas;

public record SalaListDTO(Long id, String nome, int capacidade, int piso, boolean disponibilidade, String tipoSala) {
}