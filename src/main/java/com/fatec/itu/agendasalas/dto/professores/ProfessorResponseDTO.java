package com.fatec.itu.agendasalas.dto.professores;

public record ProfessorResponseDTO(
    Long usuarioId,
    String professorNome,
    String professorEmail,
    Long registroProfessor,
    Long cargoId
){}
