package com.fatec.itu.agendasalas.dto.recursosSalasDTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record RecursoSalaListaCreationDTO(
    @NotEmpty(message="Não pode mandar uma lista vazia de recursos")
    List<RecursoSalaIndividualCreationDTO> listaDeRecursosParaAdicionar
){}
