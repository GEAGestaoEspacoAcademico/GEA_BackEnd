package com.fatec.itu.agendasalas.dto.disciplinas;

public record DisciplinaListDTO(
    Long disciplinaId,
    String disciplinaNome,
    Long semestreId,
    String semestreNome,
    String cursoNome
){}

