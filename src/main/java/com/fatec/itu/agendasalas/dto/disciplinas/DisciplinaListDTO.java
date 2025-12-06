package com.fatec.itu.agendasalas.dto.disciplinas;

public record DisciplinaListDTO(
    Long disciplinaId,
    String disciplinaNome,
    Long semestreId,
    String semestreNome,
    Long cursoId,
    String cursoNome
){}

