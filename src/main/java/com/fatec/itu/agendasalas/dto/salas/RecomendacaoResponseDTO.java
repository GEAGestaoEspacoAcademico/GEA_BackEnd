package com.fatec.itu.agendasalas.dto.salas;

import java.util.List;

public record RecomendacaoResponseDTO(
    List<SalaDetailDTO> recomendacoes,
    List<SalaDetailDTO> outrasOpcoes
) { }
