package com.fatec.itu.agendasalas.dto.cursos;

public record CursoListDTO(
    Long cursoId, 
    String cursoNome, 
    Long usuarioId, 
    String cursoSigla, 
    Long coordenadorId,
    String coordenadorNome
){}
