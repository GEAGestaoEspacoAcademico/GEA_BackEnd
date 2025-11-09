package com.fatec.itu.agendasalas.dto.janelasHorario;

import java.time.LocalTime;

public record JanelasHorarioResponseDTO(
    Long janelasHorarioId, 
    LocalTime horaInicio, 
    LocalTime horaFim) 
{}
