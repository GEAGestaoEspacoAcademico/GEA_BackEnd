package com.fatec.itu.agendasalas.dto.professores;

import java.util.List;

public record ProfessorUpdateDTO(
        Long usuarioId,
        String nome,
        String email,
        Long cargoId,
        List<Long> disciplinasIds
) {}

