package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fatec.itu.agendasalas.dto.salas.SalaResumoDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgendamentoNotificacaoDisciplinaDTO(
    Long agendamentoNotificacaoId,
    SalaResumoDTO sala, 
    LocalDate dataInicio,
    LocalDate dataFim,
    LocalTime horaInicio,
    LocalTime horaFim,
    Long disciplinaId,
    String disciplinaNome,
    boolean isEvento
) {}
