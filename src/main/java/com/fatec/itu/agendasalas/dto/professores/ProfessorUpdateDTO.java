package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

public record ProfessorUpdateDTO(
       
        String nome,
        String email,
        Long registroProfessor,
        Long cargoId,
        List<Long> disciplinasIds
) {}

