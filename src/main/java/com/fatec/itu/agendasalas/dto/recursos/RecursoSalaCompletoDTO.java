package com.fatec.itu.agendasalas.dto.recursos;

public record RecursoSalaCompletoDTO(
    Long recursoSalaId, 
    String recursoSalaNome, 
    String recursoSalaTipo, 
    Integer quantidadeRecurso
){}
