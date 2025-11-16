package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DEVAgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotNull(message = "O local do evento não pode ser nulo")   
Long localId,

@NotNull(message = "O usuario nao pode ser nulo")
Long usuario,

@NotNull (message = "Seu evento deve ter pelo menos 1 dia")
List<DEVAgendamentoEventoDiasAgendadosDTO> diasAgendados

) {

}
