package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotBlank(message = "O local do evento não pode ser nulo")   
Long local,

@NotNull (message = "Seu evento deve ter pelo menos 1 dia")
List<AgendamentoEventoDiasAgendadosDTO> diasAgendados,

@NotNull(message = "O usuario nao pode ser nulo")
Long usuario

) {

}
