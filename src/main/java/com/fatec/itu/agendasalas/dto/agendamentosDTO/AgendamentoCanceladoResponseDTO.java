package com.fatec.itu.agendasalas.dto.agendamentosDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoCanceladoResponseDTO {

    private Long idCancelamento;
    private Long agendamentoOriginalId;
    private String tipoAgendamento;
    private String motivoCancelamento;
    private LocalDateTime dataHoraCancelamento;
}