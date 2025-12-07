package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
    @NotNull (message="o usuario não pode ser nulo")
    Long usuarioId,

    @NotBlank (message="O nome do evento não pode ser nulo")
    String eventoNome,

    @NotNull (message="O id da sala não pode ser nulo")
    Long salaId,

    @NotEmpty(message="Envie pelo menos 1 dia pra ser agendado 1 evento")
    List<AgendamentoEventoDiasAgendadosDTO> dias
    ) {
}