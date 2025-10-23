package com.fatec.itu.agendasalas.dto;

import java.util.List;

public record SalaCreateDTO(
        String nome,
        int capacidade,
        int piso,
        boolean disponibilidade,
        boolean isLaboratorio,
        List<RecursoDaSalaDTO> recursos,
        String observacoes) {
}