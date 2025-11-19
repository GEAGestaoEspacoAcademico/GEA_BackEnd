package com.fatec.itu.agendasalas.dto.recursos;

public record RecursoCompletoComQuantidadeDTO(
        Long recursoId,
        String recursoNome,
        Long recursoTipoId,
        Integer quantidade
){}
