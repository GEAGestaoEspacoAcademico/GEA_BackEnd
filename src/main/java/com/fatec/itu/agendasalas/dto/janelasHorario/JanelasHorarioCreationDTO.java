package com.fatec.itu.agendasalas.dto.janelasHorario;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record JanelasHorarioCreationDTO(
    @NotNull LocalTime horaInicio, 
    @NotNull LocalTime horaFim){
    
}