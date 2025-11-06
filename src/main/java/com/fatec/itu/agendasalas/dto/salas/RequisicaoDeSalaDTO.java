package com.fatec.itu.agendasalas.dto.salas;

import java.time.LocalDate;
import java.util.Set;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioCreationDTO;
import jakarta.validation.constraints.NotNull;

public record RequisicaoDeSalaDTO(JanelasHorarioCreationDTO horarios, LocalDate data,
    Long tipoSalaId,
    @NotNull(
        message = "O campo 'recursoIds' é obrigatório mas pode ser uma lista vazia.") Set<Long> recursosIds,
    @NotNull(message = "O campo 'capacidade' é obrigatório.") Integer capacidade) {
}
