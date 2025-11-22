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

import com.fatec.itu.agendasalas.dto.cursos.CursoCreateDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoListDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.services.CursoService;
import com.fatec.itu.agendasalas.services.DisciplinaService;

import io.swagger.v3.oas.annotations.Operation;
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

    private final DisciplinaService disciplinaService;

    @Autowired
    private CursoService cursoService;

    CursoController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Operation(summary = "Cria um novo curso")
    @PostMapping
    public CursoListDTO criarCurso(@RequestBody CursoCreateDTO curso) {
        return cursoService.criar(curso);
    }

    @Operation(summary = "Lista todos os cursos existentes")
    @GetMapping
    public List<CursoListDTO> listarCursos() {
        return cursoService.listar();
    }

    @Operation(summary = "Apresenta um único curso pelo seu id")
    @GetMapping("{cursoId}")
    public CursoListDTO buscarPorId(@PathVariable Long cursoId) {
        return cursoService.buscarPorId(cursoId);
    }

    @Operation(summary = "Atualiza um curso existente pelo seu id")
    @PutMapping("{cursoId}")
    public CursoListDTO editarCurso(@PathVariable Long cursoId,
            @RequestBody CursoCreateDTO novoCurso) {
        return cursoService.atualizar(cursoId, novoCurso);
    }

    @Operation(summary = "Deleta um curso existente pelo seu id")
    @DeleteMapping("{cursoId}")
    public void excluirCurso(@PathVariable Long cursoId) {
        cursoService.excluir(cursoId);
    }

    @Operation(summary = "Lista todas as disciplinas pelo id do Curso")
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200",
            description = "Disciplinas encontradas",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "array", implementation = DisciplinaListDTO.class),
                examples = @ExampleObject(
                    value = "[\n" +
                            "  {\n" +
                            "    \"disciplinaId\": 1,\n" +
                            "    \"disciplinaNome\": \"Engenharia de Software III\",\n" +
                            "    \"disciplinaSemestre\": \"2025.2\",\n" +
                            "    \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\"\n" +
                            "  }\n" +
                            "]"
                )
            )
        ),

    @ApiResponse(responseCode = "404",
        description = "Curso não encontrado",
        content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{cursoId}/disciplinas")
    public ResponseEntity<List<DisciplinaListDTO>> listarDisciplinasPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(disciplinaService.listarDisciplinasPorCurso(cursoId));
    }  
}
