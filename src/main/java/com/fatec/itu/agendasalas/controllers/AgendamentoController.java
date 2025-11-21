package com.fatec.itu.agendasalas.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                schema = @Schema(type = "array", implementation = AgendamentoNotificacaoDisciplinaDTO.class)))
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
                schema = @Schema(type = "array", implementation = AgendamentoNotificacaoDisciplinaDTO.class))),
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
}
