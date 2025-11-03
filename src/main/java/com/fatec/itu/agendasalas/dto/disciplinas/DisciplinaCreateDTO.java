package com.fatec.itu.agendasalas.dto.disciplinas;

public record DisciplinaCreateDTO(
    Long cursoId,    
    String disciplinaNome, 
    String disciplinaSemestre
    ) {

    }
