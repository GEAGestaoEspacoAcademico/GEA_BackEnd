package com.fatec.itu.agendasalas.dto;

import java.time.LocalTime;

public record JanelasHorarioResponseDTO(Long id, LocalTime horaInicio, LocalTime horaFim) {

}
