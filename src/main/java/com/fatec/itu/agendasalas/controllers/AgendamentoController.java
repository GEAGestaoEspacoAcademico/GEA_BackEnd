package com.fatec.itu.agendasalas.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;
import com.fatec.itu.agendasalas.services.AgendamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("agendamentos")
@Tag(name = "Agendamento", description = "Operações relacionadas a agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarAgendamento(
        @PathVariable Long id,
        @RequestBody AgendamentoCanceladoRequestDTO request) {

            if (request.motivoCancelamento() == null || request.motivoCancelamento().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O motivo do cancelamento é obrigatório.");
            }

            if (request.usuarioId() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário é obrigatório.");
            }

            try {
        
                Usuario usuarioCancelador = usuarioRepository.findById(request.usuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

                AgendamentoCanceladoResponseDTO responseDTO =
                agendamentoService.cancelarAgendamento(id, usuarioCancelador, request);

                return ResponseEntity.ok(responseDTO);

            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(404).body(e.getMessage());

            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());

            } catch (IllegalStateException e) {
                return ResponseEntity.status(409).body(e.getMessage());

            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Erro inesperado no cancelamento: " + e.getMessage());
            }
    }
}
