package com.fatec.itu.agendasalas.dto.paginacaoDTO;

import java.util.List;

import org.springframework.data.domain.Page;



public record PageableResponseDTO<T> (
    List<T> conteudo,
    int numeroDaPagina,
    int tamanhoDaPagina,
    long totalDeElementos,
    int totalDePaginas,
    boolean ultimaPagina
){

   public static <T> PageableResponseDTO<T> fromPage(Page<T> page) {
    return new PageableResponseDTO<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isLast()
    );
}

}
