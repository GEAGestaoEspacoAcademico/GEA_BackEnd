package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record AgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotBlank(message = "O local do evento não pode ser nulo")   
String local,

LocalDateTime diaInicio,
LocalDateTime diaFim,




) {

}
