package com.fatec.itu.agendasalas.dto.professores;

public record ProfessorResponseDTO(
    Long professorId,
    String professorNome,
    String professorEmail,
    Long registroProfessor,
    Long cargoId
){}
