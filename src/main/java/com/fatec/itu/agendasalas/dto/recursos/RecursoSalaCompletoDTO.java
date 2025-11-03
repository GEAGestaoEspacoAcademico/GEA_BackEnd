package com.fatec.itu.agendasalas.dto.recursos;

public record RecursoSalaCompletoDTO(
    Long recursoSalaCompletoId, 
    String recursoSalaCompletoNome, 
    String recursoSalaCompletoTipo, 
    Integer quantidade) {
  
}
