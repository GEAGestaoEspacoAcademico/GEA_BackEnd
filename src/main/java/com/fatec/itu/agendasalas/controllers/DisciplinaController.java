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

    @Operation(summary = "Cria uma nova disciplina", description = "Endpoint para criação de disciplinas vinculadas a um curso.", responses = {
            @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaListDTO.class), examples = @ExampleObject(name = "Exemplo de resposta 201", value = "{\n"
                    +
                    "  \"disciplinaId\": 31,\n" +
                    "  \"disciplinaNome\": \"Computação Gráfica\",\n" +
                    "  \"disciplinaSemestre\": \"2025.2\",\n" +
                    "  \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\"\n" +
                    "}")))
    })
    @PostMapping
    public DisciplinaListDTO criarDisciplina(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para criação de uma nova disciplina", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaCreateDTO.class), examples = @ExampleObject(name = "Exemplo de criação de disciplina", value = "{\n"
                    +
                    "  \"cursoId\": \"1\",\n" +
                    "  \"disciplinaNome\": \"Computação Gráfica\",\n" +
                    "  \"disciplinaSemestre\": \"2025.2\"\n" +
                    "}"))) @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.criar(novaDisciplina);
    }

    @Operation(summary = "Lista todas as disciplinas existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de disciplinas encontrada", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = DisciplinaListDTO.class), examples = @ExampleObject(value = "[ { \"disciplinaId\": 1, \"disciplinaNome\": \"Algoritmos\", \"semestreId\": 1, \"semestreNome\": \"2025.1\", \"cursoId\": 1, \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\" }, { \"disciplinaId\": 2, \"disciplinaNome\": \"Banco de Dados\", \"semestreId\": 1, \"semestreNome\": \"2025.1\", \"cursoId\": 1, \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\" } ]")))
    })
    @GetMapping
    public ResponseEntity<List<DisciplinaListDTO>> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaService.listar());
    }

    @Operation(summary = "Apresenta uma disciplina existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaListDTO.class), examples = @ExampleObject(value = "{ \"id\": 1, \"nome\": \"Algoritmos\" }"))),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @GetMapping("{disciplinaId}")
    public DisciplinaListDTO buscarPorId(
            @Parameter(description = "ID da disciplina a ser buscada") @PathVariable Long disciplinaId) {
        return disciplinaService.buscarPorId(disciplinaId);
    }

    @Operation(summary = "Atualiza uma disciplina existente")
    @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Disciplina atualizada com sucesso",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DisciplinaListDTO.class),
            examples = @ExampleObject(
                value = """
                        {
                          "disciplinaId": 1,
                          "disciplinaNome": "Computação em Nuvem",
                          "disciplinaSemestre": "2025.1",
                          "cursoNome": "Gestão da Tecnologia da Informação"
                        }
                        """
            )
        )
    )
    })
    @PutMapping("{disciplinaId}")
    public ResponseEntity<DisciplinaListDTO> editarDisciplina(
    @Parameter(
        description = "ID da disciplina a ser atualizada",
        required = true
    )
    @PathVariable Long disciplinaId,

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Novos dados para a disciplina",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DisciplinaCreateDTO.class),
            examples = @ExampleObject(
                value = """
                        {
                          "cursoId": "2",
                          "disciplinaNome": "Computação em Nuvem",
                          "disciplinaSemestre": "2025.1"
                        }
                        """
            )
        )
    )
    @RequestBody DisciplinaCreateDTO novaDisciplina
    ){
        return ResponseEntity.ok(disciplinaService.atualizar(disciplinaId, novaDisciplina));
    }      

    @Operation(
        summary = "Desativa uma disciplina pelo ID",
        description = "Desativa a disciplina informada, desfazendo vínculos com curso e professor, e convertendo agendamentos de aula em cancelados. A disciplina não é removida fisicamente do banco, apenas marcada como excluída.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Disciplina desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
        }
    )
    @DeleteMapping("{disciplinaId}")
    public ResponseEntity<Void> excluirDisciplina(
        @Parameter(description = "ID da disciplina a ser desativada") @PathVariable Long disciplinaId) {
        try {
            disciplinaService.excluir(disciplinaId);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
