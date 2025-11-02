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

    @Operation(summary = "Lista todos os professores existentes")
    @GetMapping("listar")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarProfessores());
    }

    @Operation(summary = "Lista todos os cursos que o professor ministra aula")
    @GetMapping("/{professorId}/cursos")
    public List<CursoListByProfessorDTO> listarPorProfessor(@PathVariable Long professorId) {
        return professorService.listarCursosPorProfessor(professorId);
    }

    @Operation(summary = "Lista todas as disciplina de um professor")
    @GetMapping("{professorId}/disciplinas")
    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(@PathVariable Long professorId) {
        return disciplinaService.listarDisciplinasPorProfessor(professorId);
    }

    @Operation(summary = "Lista um professor existente pelo seu id")
    @GetMapping("listar/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long professorId) {
        ProfessorResponseDTO professor = professorService.buscarPorId(professorId);
        return ResponseEntity.ok(professor);
    }

    @Operation(summary = "Deleta professor existente pelo registro do professor")
    @DeleteMapping("{registroProfessor}")
    public ResponseEntity<Void> delete(@PathVariable long registroProfessor) {
        professorService.excluirProfessor(registroProfessor);
        return ResponseEntity.noContent().build();
    }
}
