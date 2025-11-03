package com.fatec.itu.agendasalas.dto.paginacaoDTO;

import java.util.List;


public record PageableResponseDTO<T> (
    List<T> conteudo,
    int numeroDaPagina,
    int tamanhoDaPagina,
    long totalDeElementos,
    int totalDePaginas,
    boolean ultimaPagina
){}
