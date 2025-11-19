package com.fatec.itu.agendasalas.dto.recursosSalasDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecursoSalaIndividualCreationDTO(
    @NotNull(message="Não pode ser nulo o id do recurso")
    Long recursoId,

    @NotNull(message="A quantidade de recurso não pode ser nula") 
    @Min(value = 1, message = "A quantidade deve ser maior que 0")
    Integer quantidadeRecurso,

    @NotBlank(message = "O tipo não pode ser nulo")
    String tipo
){}
