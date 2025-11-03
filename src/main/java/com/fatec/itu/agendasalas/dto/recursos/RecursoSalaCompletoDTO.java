package com.fatec.itu.agendasalas.dto.recursos;

public record RecursoSalaCompletoDTO(
    Long idRecurso,
    String nome,
    Long tipoId,
    Integer quantidade) { }
