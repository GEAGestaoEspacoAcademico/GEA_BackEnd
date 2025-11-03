package com.fatec.itu.agendasalas.dto.disciplinas;

public record DisciplinaListDTO(
    Long disciplinaId, 
    String disciplinaNome, 
    String disciplinaSemestre, 
    String cursoNome) {

}
