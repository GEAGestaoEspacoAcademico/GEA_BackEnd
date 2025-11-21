package com.fatec.itu.agendasalas.controllers;

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

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorCreateDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorUpdateDTO;
import com.fatec.itu.agendasalas.services.DisciplinaService;
import com.fatec.itu.agendasalas.services.ProfessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de professores encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = ProfessorResponseDTO.class)))
    })
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarProfessores());
    }

    @Operation(summary = "Cadastra um novo professor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Professor criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorResponseDTO.class)))
    })
    @PostMapping
    /*Se não me engano é função da secretaria, mas isso é o de menos é só adicionar aqui */
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<ProfessorResponseDTO> cadastrarProfessor(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do professor",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfessorCreateDTO.class)))
            @RequestBody ProfessorCreateDTO professorCreateDTO){
        return ResponseEntity.created(null).body(professorService.cadastrarUsuario(professorCreateDTO));
    }

    @Operation(summary = "Busca professor pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Professor encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(
        @Parameter(description = "ID do professor") @PathVariable Long professorId) {
        ProfessorResponseDTO professor = professorService.buscarPorId(professorId);
        return ResponseEntity.ok(professor);
    }

    @Operation(summary = "Lista os cursos que o professor ministra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cursos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CursoListByProfessorDTO.class)))
    })
    @GetMapping("/{professorId}/cursos")
    public List<CursoListByProfessorDTO> listarCursosPorProfessor(
        @Parameter(description = "ID do professor") @PathVariable Long professorId) {
        return professorService.listarCursosPorProfessor(professorId);
    }

    @Operation(summary = "Lista as disciplinas do professor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disciplinas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = DisciplinaListDTO.class)))
    })
    @GetMapping("/{professorId}/disciplinas")
    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(
        @Parameter(description = "ID do professor") @PathVariable Long professorId) {
        return disciplinaService.listarDisciplinasPorProfessor(professorId);
    }

    @Operation(summary = "Deleta professor existente pelo registro do professor")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Professor deletado") })
    @DeleteMapping("{registroProfessor}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "Registro (matrícula) do professor") @PathVariable long registroProfessor) {
        professorService.excluirProfessor(registroProfessor);
        return ResponseEntity.noContent().build();
    }    

    @Operation(summary = "Atualiza professor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Professor atualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorResponseDTO.class)))
    })
    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(
        @Parameter(description = "ID do professor") @PathVariable Long professorId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do professor",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProfessorUpdateDTO.class)))
        @RequestBody ProfessorUpdateDTO dto) {
            ProfessorUpdateDTO dtoComId = new ProfessorUpdateDTO(
                professorId,
                dto.nome(),
                dto.email(),
                dto.cargoId(),
                dto.disciplinasIds()
            );
            return ResponseEntity.ok(
                professorService.atualizarProfessor(professorId, dto)
    );
    }
}
