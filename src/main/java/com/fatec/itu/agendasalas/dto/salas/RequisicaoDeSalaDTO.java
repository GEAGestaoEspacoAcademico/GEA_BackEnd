package com.fatec.itu.agendasalas.dto.salas;

import java.time.LocalDate;
import java.util.List;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioCreationDTO;
import com.fatec.itu.agendasalas.entity.Recurso;

public record RequisicaoDeSalaDTO(JanelasHorarioCreationDTO horarios, LocalDate data,
    Long tipoSalaId, List<Recurso> recursos, int capacidade) {

}
