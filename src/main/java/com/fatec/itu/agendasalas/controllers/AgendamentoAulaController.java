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
import com.fatec.itu.agendasalas.services.AgendamentoAulaService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("agendamentos/aulas")
public class AgendamentoAulaController {

    @Autowired
    private AgendamentoAulaService agendamentoAulaService;

    @PostMapping
    public ResponseEntity<AgendamentoAulaResponseDTO> criarAgendamentoAula(
            @RequestBody @Valid AgendamentoAulaCreationDTO dto) {
        try {
            AgendamentoAulaResponseDTO response = agendamentoAulaService.criarAgendamentoAula(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> listarTodosAgendamentosAula() {
        List<AgendamentoAulaResponseDTO> agendamentos = agendamentoAulaService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoAulaResponseDTO> buscarAgendamentoAulaPorId(@PathVariable Long id) {
        try {
            AgendamentoAulaResponseDTO response = agendamentoAulaService.buscarPorId(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorDisciplina(
            @PathVariable Integer disciplinaId) {
        List<AgendamentoAulaResponseDTO> agendamentos = agendamentoAulaService.buscarPorDisciplina(disciplinaId);
        return ResponseEntity.ok(agendamentos);
    }


    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorProfessor(
            @PathVariable Integer professorId) {
        List<AgendamentoAulaResponseDTO> agendamentos = agendamentoAulaService.buscarPorProfessor(professorId);
        return ResponseEntity.ok(agendamentos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoAulaResponseDTO> atualizarAgendamentoAula(
            @PathVariable Long id,
            @RequestBody AgendamentoAulaCreationDTO dto) {
        try {
            AgendamentoAulaResponseDTO response = agendamentoAulaService.atualizarAgendamentoAula(id, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamentoAula(@PathVariable Long id) {
        try {
            agendamentoAulaService.excluirAgendamentoAula(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
