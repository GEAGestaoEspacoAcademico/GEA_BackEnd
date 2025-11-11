package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotBlank(message = "O local do evento não pode ser nulo")   
String local,

@NotNull(message = "O dia de inicio de agendamento de um evento não pode ser nulo")
LocalDate diaInicio,

@NotNull(message = "O dia de fim de agendamento de um evento não pode ser nulo")
LocalDate diaFim,

@NotNull(message = "O horario de inicio de agendamento de um evento não pode ser nulo")
LocalTime horaInicio,

@NotNull(message = "O horario de fim de agendamento de um evento não pode ser nulo")
LocalTime horafim,

boolean todosHorarios




) {

}
