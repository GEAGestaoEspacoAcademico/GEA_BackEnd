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

import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaCreateDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.services.DisciplinaService;

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
@RequestMapping("disciplinas")
@Tag(name = "Disciplina", description = "Operações relacionadas a disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Operation(summary = "Cria uma nova disciplina")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Disciplina criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisciplinaListDTO.class),
                examples = @ExampleObject(value = "{ \"id\": 10, \"nome\": \"Estrutura de Dados\" }")))
    })
    @PostMapping
    public DisciplinaListDTO criarDisciplina(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de uma nova disciplina",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DisciplinaCreateDTO.class),
                    examples = @ExampleObject(value = "{ \"nome\": \"Estrutura de Dados\" }")))
            @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.criar(novaDisciplina);
    }

    @Operation(summary = "Lista todas as disciplinas existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de disciplinas encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = DisciplinaListDTO.class),
                examples = @ExampleObject(value = "[ { \"id\": 1, \"nome\": \"Algoritmos\" }, { \"id\": 2, \"nome\": \"Banco de Dados\" } ]")))
    })
    @GetMapping
    public List<DisciplinaListDTO> listarDisciplinas() {
        return disciplinaService.listar();
    }

    @Operation(summary = "Apresenta uma disciplina existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Disciplina encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisciplinaListDTO.class),
                examples = @ExampleObject(value = "[ { \"disciplinaId\": 1, \"disciplinaNome\": \"Engenharia de Software III\", \"disciplinaSemestre\": \"2025.2\", \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\" } ]"))),
        @ApiResponse(responseCode = "404", 
            description = "Disciplina não encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(),
                examples = @ExampleObject(value = "{ \"status\": 404, \"error\": \"Not Found\", \"message\": \"Disciplina não encontrada\", \"path\": \"/disciplinas/9999\", \"timestamp\": \"2025-11-23T22:58:51-03:00\" }"))),
        @ApiResponse(responseCode = "500", 
            description = "Disciplina não encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(),
                examples = @ExampleObject(value = "{ \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Usuário não encontrado\", \"path\": \"/usuarios/64\", \"timestamp\": \"2025-11-23T22:58:51-03:00\" }")))
    })
    @GetMapping("{disciplinaId}")
    public DisciplinaListDTO buscarPorId(
        @Parameter(description = "ID da disciplina a ser buscada") @PathVariable Long disciplinaId) {
        return disciplinaService.buscarPorId(disciplinaId);
    }

    @Operation(summary = "Atualiza uma disciplina pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Disciplina atualizada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisciplinaListDTO.class),
                examples = @ExampleObject(value = "{ \"id\": 1, \"nome\": \"Algoritmos e Estruturas\" }"))),
        @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @PutMapping("{disciplinaId}")
    public DisciplinaListDTO editarDisciplina(@Parameter(description = "ID da disciplina a ser atualizada") @PathVariable Long disciplinaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Novos dados para a disciplina",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DisciplinaCreateDTO.class),
                    examples = @ExampleObject(value = "{ \"nome\": \"Algoritmos e Estruturas\" }")))
            @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.atualizar(disciplinaId, novaDisciplina);
    }

    @Operation(summary = "Deleta uma disciplina pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disciplina excluída com sucesso"), // Retorna 200 ou 204
        @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @DeleteMapping("{disciplinaId}")
    public void excluirDisciplina(
        @Parameter(description = "ID da disciplina a ser excluída") @PathVariable Long disciplinaId) {
        disciplinaService.excluir(disciplinaId);
    }
}
