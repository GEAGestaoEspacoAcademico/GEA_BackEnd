package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationByAuxiliarDocenteDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationComRecorrenciaDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.exceptions.AgendamentoComHorarioIndisponivelException;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.services.AgendamentoAulaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("agendamentos/aulas")
@Tag(name = "Agendamento")
public class AgendamentoAulaController {

    @Autowired
    private AgendamentoAulaService agendamentoAulaService;

    @Operation(summary = "Cria um novo agendamento de aula")
    @PostMapping
    public ResponseEntity<Void> criarAgendamentoAula(
            @RequestBody @Valid AgendamentoAulaCreationDTO dto) {
        try {
            agendamentoAulaService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AgendamentoComHorarioIndisponivelException a) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary="Cria um novo agendamento de aula em um dia específico, ")
    @PostMapping("/auxiliar-docente")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> criarAgendamentoAulaByAD(@Valid @RequestBody AgendamentoAulaCreationByAuxiliarDocenteDTO dto){
        return ResponseEntity.created(null).body(agendamentoAulaService.criarAgendamentoAulaByAD(dto));
    }


    @Operation(summary = "Cria agendamentos de aula recorrentes entre duas datas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Created",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(
                    value = "[{ \"agendamentoAulaId\": 16, \"usuarioNome\": \"Prof. Fabricio Londero\", \"salaId\": 6, \"salaNome\": \"Lab 306\", \"disciplinaId\": 2, \"disciplinaNome\": \"Laboratório de Banco de Dados\", \"semestre\": \"2025.2\", \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"professorNome\": \"Prof. Fabricio Londero\", \"data\": \"2026-02-02\", \"diaDaSemana\": \"Segunda-feira\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"isEvento\": false }]")))

    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do agendamento",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AgendamentoAulaCreationComRecorrenciaDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"usuarioId\": 7, \"dataInicio\": \"2026-02-02\", \"dataFim\": \"2026-06-06\", \"diaDaSemana\": \"SEGUNDA\", \"janelasHorarioId\": [1,2,3,4], \"disciplinaId\": 2, \"salaId\": 6 }")))
    @PostMapping("/recorrencia")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> criarAgendamentoAulaComRecorrencia(@Valid @RequestBody AgendamentoAulaCreationComRecorrenciaDTO dto) {
        try {
            List<AgendamentoAulaResponseDTO> agendamentosCriados = agendamentoAulaService.criarAgendamentoAulaComRecorrencia(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentosCriados);
        } catch (ConflitoAoAgendarException c) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Lista todos os agendamentos de aula")
    @GetMapping
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> listarTodosAgendamentosAula() {
        List<AgendamentoAulaResponseDTO> agendamentos = agendamentoAulaService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Apresenta um agendamento de aula pelo seu id")
    @GetMapping("/{agendamentoAulaId}")
    public ResponseEntity<AgendamentoAulaResponseDTO> buscarAgendamentoAulaPorId(
            @PathVariable Long agendamentoAulaId) {
        try {
            AgendamentoAulaResponseDTO response =
                    agendamentoAulaService.buscarPorId(agendamentoAulaId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Lista todos os agendamentos de aula por disciplina")
    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorDisciplina(
            @PathVariable Integer disciplinaId) {
        List<AgendamentoAulaResponseDTO> agendamentos =
                agendamentoAulaService.buscarPorDisciplina(disciplinaId);
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Lista todos os agendamentos de aula por professor")
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorProfessor(
            @PathVariable Integer professorId) {
        List<AgendamentoAulaResponseDTO> agendamentos =
                agendamentoAulaService.buscarPorProfessor(professorId);
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Atualiza um agendamento de aula pelo seu id")
    @PutMapping("/{agendamentoAulaId}")
    public ResponseEntity<AgendamentoAulaResponseDTO> atualizarAgendamentoAula(
            @PathVariable Long agendamentoAulaId, @RequestBody AgendamentoAulaCreationDTO dto) {
        try {
            AgendamentoAulaResponseDTO response =
                    agendamentoAulaService.atualizarAgendamentoAula(agendamentoAulaId, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deleta um agendamento de aula pelo seu id")
    @DeleteMapping("/{agendamentoAulaId}")
    public ResponseEntity<Void> excluirAgendamentoAula(@PathVariable Long agendamentoAulaId) {
        try {
            agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
