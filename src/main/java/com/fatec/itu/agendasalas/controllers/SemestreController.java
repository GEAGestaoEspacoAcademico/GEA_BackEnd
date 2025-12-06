package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.semestreDTO.SemestreListDTO;
import com.fatec.itu.agendasalas.services.SemestreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("semestres")
@Tag(name = "Semestres", description = "Operações relacionadas aos semestres acadêmicos")
public class SemestreController {

    @Autowired
    private SemestreService semestreService;

    @Operation(summary = "Lista todos os semestres cadastrados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SemestreListDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        [
                            { "id": 1, "nome": "1º semestre" },
                            { "id": 2, "nome": "2º semestre" },
                            { "id": 3, "nome": "3º semestre" }
                        ]
                        """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<SemestreListDTO>> listarTodos() {
        List<SemestreListDTO> semestres = semestreService.listarTodos();
        return ResponseEntity.ok(semestres);
    }
}
