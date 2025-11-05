package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

public record ProfessorUpdateDTO(
        Long registroProfessor,
        String nome,
        String email,
        List<Long> cargosIds,
        List<Long> disciplinasIds
) {}
