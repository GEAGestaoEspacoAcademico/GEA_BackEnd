package com.fatec.itu.agendasalas.dto.janelasHorario;

import java.time.LocalTime;

public record JanelasHorarioUpdateDTO(
    LocalTime horaInicio, 
    LocalTime horaFim
){}
