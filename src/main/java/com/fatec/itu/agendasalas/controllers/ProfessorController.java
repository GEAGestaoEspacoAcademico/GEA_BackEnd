package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorCreateDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorUpdateDTO;
import com.fatec.itu.agendasalas.services.DisciplinaService;
import com.fatec.itu.agendasalas.services.ProfessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarProfessores());
    }

    @PostMapping
    /*Se não me engano é função da secretaria, mas isso é o de menos é só adicionar aqui */
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<ProfessorResponseDTO> cadastrarProfessor(@Valid @RequestBody ProfessorCreateDTO professorCreateDTO){

        ProfessorResponseDTO professorResponseDTO = professorService.cadastrarUsuario(professorCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(professorResponseDTO.usuarioId()).toUri();

        return ResponseEntity.created(uri).body(professorResponseDTO);
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

    @Operation(summary = "Deleta professor existente pelo registro do professor")
    @DeleteMapping("{registroProfessor}")
    public ResponseEntity<Void> delete(@PathVariable long registroProfessor) {
        professorService.excluirProfessor(registroProfessor);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza professor existente")
    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(
        @PathVariable Long professorId,
        @RequestBody ProfessorUpdateDTO dto) {
            
            return ResponseEntity.ok(
                professorService.atualizarProfessor(professorId, dto)
    );
    }

  
}
