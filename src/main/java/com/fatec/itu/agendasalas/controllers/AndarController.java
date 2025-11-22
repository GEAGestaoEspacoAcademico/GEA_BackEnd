package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.andaresDTO.AndarResponseDTO;
import com.fatec.itu.agendasalas.entity.Andar;
import com.fatec.itu.agendasalas.services.AndarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/andares")
@RequiredArgsConstructor
@Tag(name = "Andares", description = "Operações relacionadas aos andares do prédio")
public class AndarController {

    private final AndarService andarService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca um andar pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Andar encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AndarResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"id\": 1, \"nome\": \"1º Andar\" }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Andar não encontrado")
    })
    public ResponseEntity<AndarResponseDTO> buscarPorId(
            @Parameter(description = "ID do andar a ser buscado") @PathVariable Long id) {

        Andar andar = andarService.buscarPorId(id);
        return ResponseEntity.ok(new AndarResponseDTO(andar.getId(), andar.getNome()));
    }

    @GetMapping
    @Operation(summary = "Lista todos os andares")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de andares encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "array", implementation = AndarResponseDTO.class),
                examples = @ExampleObject(
                    value = "[ { \"id\": 1, \"nome\": \"1º Andar\" }, { \"id\": 2, \"nome\": \"2º Andar\" } ]"
                )
            )
        )
    })
    public ResponseEntity<List<AndarResponseDTO>> listar() {
        return ResponseEntity.ok(andarService.listar());
    }
}


