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

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.exceptions.AgendamentoComHorarioIndisponivelException;
import com.fatec.itu.agendasalas.services.AgendamentoAulaService;

import io.swagger.v3.oas.annotations.Operation;
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
