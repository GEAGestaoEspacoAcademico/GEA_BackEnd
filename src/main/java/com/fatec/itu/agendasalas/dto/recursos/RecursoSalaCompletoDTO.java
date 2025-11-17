package com.fatec.itu.agendasalas.dto.recursos;

public record RecursoSalaCompletoDTO(
    Long recursoId, 
    String recursoNome, 
    Long recursoTipoId, 
    Integer quantidadeRecurso
){}
