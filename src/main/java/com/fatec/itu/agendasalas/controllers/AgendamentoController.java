package com.fatec.itu.agendasalas.controllers;

import java.time.LocalDate;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoCanceladoRequestDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoCanceladoResponseDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDisciplinaDTO;
 
import com.fatec.itu.agendasalas.services.AgendamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 

@CrossOrigin
@RestController
@RequestMapping("agendamentos")
@Tag(name = "Agendamento", description = "Operações relacionadas a agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(summary = "Lista todos os agendamentos existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de agendamentos encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = AgendamentoNotificacaoDisciplinaDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { \"agendamentoId\": 201, \"sala\": { \"salaId\": 5, \"salaNome\": \"Lab 301\" }, \"data\": \"2025-12-01\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"disciplinaId\": 12, \"disciplinaNome\": \"Engenharia de Software III\", \"isEvento\": false } ]")))
    })
    @GetMapping
    public ResponseEntity<List<AgendamentoNotificacaoDisciplinaDTO>> listarAgendamentos() {
        return ResponseEntity.ok(agendamentoService.listarAgendamentosDisciplina());
    }

    @Operation(summary = "Busca todos os agendamentos correspondentes à data informada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Agendamentos encontrados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = AgendamentoNotificacaoDisciplinaDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { \"agendamentoId\": 202, \"sala\": { \"salaId\": 3, \"salaNome\": \"Sala 204\" }, \"data\": \"2025-11-25\", \"horaInicio\": \"13:30:00\", \"horaFim\": \"15:10:00\", \"disciplinaId\": 7, \"disciplinaNome\": \"Redes de Computadores\", \"isEvento\": false } ]"))),
        @ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado para a data informada")
    })
    @GetMapping("/{data}")
    public ResponseEntity<List<AgendamentoNotificacaoDisciplinaDTO>> buscarAgendamentosPorData(
            @Parameter(description = "Data a ser buscada no formato YYYY-MM-DD", example = "2025-11-25")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

                List<AgendamentoNotificacaoDisciplinaDTO> agendamentos = agendamentoService.buscarAgendamentosPorData(data);

                if (agendamentos.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Cancela um agendamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoCanceladoResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                    {
                        "idCancelamento": 3,
                        "agendamentoOriginalId": 2,
                        "tipoAgendamento": "AULA",
                        "motivoCancelamento": "Professor ausente",
                        "dataHoraCancelamento": "2025-12-04T18:43:46.6149025"
                    }
                    """))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida - dados obrigatórios ausentes"),
        @ApiResponse(responseCode = "404", description = "Agendamento ou usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito ao cancelar agendamento"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado no cancelamento")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados para cancelar o agendamento",
        required = true,
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AgendamentoCanceladoRequestDTO.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                value = "{ \"motivoCancelamento\": \"Professor ausente\", \"usuarioId\": 5 }")))
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoCanceladoResponseDTO> cancelarAgendamento(
        @Parameter(description = "ID do agendamento a ser cancelado") @PathVariable Long id,
        @RequestBody AgendamentoCanceladoRequestDTO request) {

        AgendamentoCanceladoResponseDTO responseDTO = agendamentoService.cancelarAgendamento(id, request);
        return ResponseEntity.ok(responseDTO);
    }
}
