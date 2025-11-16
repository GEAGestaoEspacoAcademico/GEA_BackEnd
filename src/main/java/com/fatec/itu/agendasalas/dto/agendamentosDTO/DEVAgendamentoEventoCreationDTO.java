<<<<<<< HEAD


package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(
@NotBlank(message = "O nome do evento não pode ser nulo")    
String nomeEvento,

@NotNull(message = "O local do evento não pode ser nulo")   
Long localId,

@NotNull(message = "O usuario nao pode ser nulo")
Long usuario,

@NotNull (message = "Seu evento deve ter pelo menos 1 dia")
List<AgendamentoEventoDiasAgendadosDTO> diasAgendados

) {

=======
package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;

public record AgendamentoEventoCreationDTO(@NotNull Long usuarioId,
    @NotNull Long salaId,
    @NotNull LocalDate data,
    @NotNull LocalTime horarioInicio,
    @NotNull LocalTime horarioFim,
    @NotNull boolean isEvento
    ) {
>>>>>>> 75eeeb7bee6fefd04da84f4d9e173d8f47b76f36
}
