package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.cursos.CursoCreateDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoListDTO;
import com.fatec.itu.agendasalas.services.CursoService;

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
@RequestMapping("cursos")
@Tag(name = "Curso", description = "Operações relacionadas a curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Cria um novo curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", // Alterado de 201 para 200 para manter a consistência da sua implementação (retorna DTO no corpo)
            description = "Curso criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CursoListDTO.class)))
    })
    @PostMapping
    public CursoListDTO criarCurso(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de um novo curso",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CursoCreateDTO.class)))
            @RequestBody CursoCreateDTO curso) {
        return cursoService.criar(curso);
    }

    @Operation(summary = "Lista todos os cursos existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de cursos encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = CursoListDTO.class)))
    })
    @GetMapping
    public List<CursoListDTO> listarCursos() {
        return cursoService.listar();
    }

    @Operation(summary = "Apresenta um único curso pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Curso encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CursoListDTO.class))),
        @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @GetMapping("{cursoId}")
    public CursoListDTO buscarPorId(
        @Parameter(description = "ID do curso a ser buscado") @PathVariable Long cursoId) {
        return cursoService.buscarPorId(cursoId);
    }

    @Operation(summary = "Atualiza um curso existente pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Curso atualizado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CursoListDTO.class))),
        @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @PutMapping("{cursoId}")
    public CursoListDTO editarCurso(@Parameter(description = "ID do curso a ser atualizado") @PathVariable Long cursoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Novos dados para o curso",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CursoCreateDTO.class)))
            @RequestBody CursoCreateDTO novoCurso) {
        return cursoService.atualizar(cursoId, novoCurso);
    }

    @Operation(summary = "Deleta um curso existente pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso excluído com sucesso"), // Retorna 200 ou 204
        @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @DeleteMapping("{cursoId}")
    public void excluirCurso(
        @Parameter(description = "ID do curso a ser excluído") @PathVariable Long cursoId) {
        cursoService.excluir(cursoId);
    }
}
