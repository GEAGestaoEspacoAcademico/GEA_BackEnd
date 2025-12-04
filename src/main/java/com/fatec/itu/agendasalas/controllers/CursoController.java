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

import com.fatec.itu.agendasalas.dto.cursos.CursoCreateDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoCreatePostDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoListDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.services.CursoService;
import com.fatec.itu.agendasalas.services.DisciplinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("cursos")
@Tag(name = "Curso", description = "Operações relacionadas a curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;

     @Operation(summary = "Cria um novo curso")
    @PostMapping
    public ResponseEntity<CursoCreatePostDTO> criarCurso(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados para criação de um novo curso",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CursoCreateDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ \"cursoNome\": \"Bacharelado em Ciência da computação\", \"coordenadorId\": 16, \"cursoSigla\": \"BCC\" }"
                )
            )
        )
        @RequestBody @Valid CursoCreateDTO dto
    ) {
        try {
            long cursoId = cursoService.criarCurso(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(new CursoCreatePostDTO(cursoId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @Operation(summary = "Lista todos os cursos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos encontrada", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CursoListDTO.class), examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "[ { \"cursoId\": 12, \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"coordenadorId\": \"18\", \"cursoSigla\": \"ADS\" } ]")))
    })
    @GetMapping
    public List<CursoListDTO> listarCursos() {
        return cursoService.listar();
    }

    @Operation(summary = "Apresenta um único curso pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoListDTO.class), examples = @ExampleObject(value = """
                    {
                        "cursoId": 1,
                        "cursoNome": "Análise e Desenvolvimento de Sistemas",
                        "cursoSigla": "ADS",
                        "usuarioId": 10,
                        "coordenadorNome": "Coord. Lucimar de Santi"
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoListDTO.class), examples = @ExampleObject(value = """
                    {
                        "status": 500,
                        "error": "Internal Server Error",
                        "message": "404 NOT_FOUND",
                        "path": "/cursos/199",
                        "timestamp": "2025-12-04T12:21:50-03:00"
                    }
                    """)))
    })
    @GetMapping("{cursoId}")
    public CursoListDTO buscarPorId(@PathVariable Long cursoId) {
        return cursoService.buscarPorId(cursoId);
    }

    @Operation(summary = "Atualiza um curso existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoListDTO.class), examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{ \"cursoId\": 12, \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"coordenadorId\": \"18\", \"cursoSigla\": \"ADS\" }"))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @PutMapping("{cursoId}")
    public CursoListDTO editarCurso(@Parameter(description = "ID do curso a ser atualizado") @PathVariable Long cursoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos dados para o curso", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoCreateDTO.class), examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{ \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"coordenadorId\": 10, \"cursoSigla\": \"ADS\" }"))) @RequestBody CursoCreateDTO novoCurso) {
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

    @Operation(summary = "Lista todas as disciplinas pelo id do Curso")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Disciplinas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = DisciplinaListDTO.class), examples = @ExampleObject(value = "[\n"
                    +
                    "  {\n" +
                    "    \"disciplinaId\": 1,\n" +
                    "    \"disciplinaNome\": \"Engenharia de Software III\",\n" +
                    "    \"disciplinaSemestre\": \"2025.2\",\n" +
                    "    \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\"\n" +
                    "  }\n" +
                    "]"))),

            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{cursoId}/disciplinas")
    public ResponseEntity<List<DisciplinaListDTO>> listarDisciplinasPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(disciplinaService.listarDisciplinasPorCurso(cursoId));
    }
}
