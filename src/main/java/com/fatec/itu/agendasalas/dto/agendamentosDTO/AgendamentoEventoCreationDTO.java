package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotBlank(message = "O local do evento não pode ser nulo")   
String local,

@NotNull(message = "O dia de inicio de agendamento de um evento não pode ser nulo")
LocalDateTime diaInicio,

@NotNull(message = "O dia de fim de agendamento de um evento não pode ser nulo")
LocalDateTime diaFim,

@NotNull(message = "O horario de inicio de agendamento de um evento não pode ser nulo")
LocalTime horaInicio,

@NotNull(message = "O horario de fim de agendamento de um evento não pode ser nulo")
LocalTime horafim




) {

}
