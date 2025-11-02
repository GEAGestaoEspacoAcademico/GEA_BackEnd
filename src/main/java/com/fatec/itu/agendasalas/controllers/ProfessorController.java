package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.services.DisciplinaService;
import com.fatec.itu.agendasalas.services.ProfessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("professores")
@Tag(name = "Professor", description = "Operações relacionadas a professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Operation(summary = "Lista todos os professores")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarProfessores());
    }

    @Operation(summary = "Busca professor pelo ID")
    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long professorId) {
        ProfessorResponseDTO professor = professorService.buscarPorId(professorId);
        return ResponseEntity.ok(professor);
    }

    @Operation(summary = "Lista os cursos que o professor ministra")
    @GetMapping("/{professorId}/cursos")
    public List<CursoListByProfessorDTO> listarCursosPorProfessor(@PathVariable Long professorId) {
        return professorService.listarCursosPorProfessor(professorId);
    }

    @Operation(summary = "Lista as disciplinas do professor")
    @GetMapping("/{professorId}/disciplinas")
    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(@PathVariable Long professorId) {
        return disciplinaService.listarDisciplinasPorProfessor(professorId);
    }

    @Operation(summary = "Deleta professor pelo ID")
    @DeleteMapping("/{professorId}")
    public ResponseEntity<Void> delete(@PathVariable Long professorId) {
        professorService.excluirProfessor(professorId);
        return ResponseEntity.noContent().build();
    }
}
