package com.fatec.itu.agendasalas.dto.eventosDTO;

import jakarta.validation.constraints.NotBlank;

public record EventoCreationDTO(

    @NotBlank(message="O nome do evento não pode ser vazio")
    String nomeEvento,

    @NotBlank(message="A descrição do evento não pode ser nula")
    String descricaoEvento
){}
