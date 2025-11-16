package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
    @NotNull Long usuarioId,
    @NotBlank String eventoNome,
    @NotNull Long salaId,
    @NotNull List<AgendamentoEventoDiasAgendadosDTO> dias
    //@NotNull LocalDate data,
    //@NotNull LocalTime horarioInicio,
    //@NotNull LocalTime horarioFim,
    //@NotNull boolean isEvento
    ) {
}