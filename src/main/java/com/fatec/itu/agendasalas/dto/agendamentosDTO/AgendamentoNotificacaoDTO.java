package com.fatec.itu.agendasalas.dto.agendamentosDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fatec.itu.agendasalas.dto.salas.SalaResumoDTO;

public record AgendamentoNotificacaoDTO(
    Long id,
    SalaResumoDTO sala, // <-- Usando o novo DTO de resumo da sala
    LocalDate dataInicio,
    LocalDate dataFim,
    LocalTime horaInicio,
    LocalTime horaFim
) { }
