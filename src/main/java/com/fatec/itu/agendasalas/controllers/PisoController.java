package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.andaresDTO.PisoResponseDTO;
import com.fatec.itu.agendasalas.entity.Piso;
import com.fatec.itu.agendasalas.services.PisoService;

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
@RequestMapping("/pisos")
@RequiredArgsConstructor
@Tag(name = "Pisos", description = "Operações relacionadas aos pisos do prédio")
public class PisoController {

    private final PisoService pisoService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca um piso pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Piso encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PisoResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"id\": 1, \"nome\": \"1º Andar\" }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Piso não encontrado")
    })
    public ResponseEntity<PisoResponseDTO> buscarPorId(
            @Parameter(description = "ID do piso a ser buscado") @PathVariable Long pisoId) {

        Piso piso = pisoService.buscarPorId(pisoId);
        return ResponseEntity.ok(new PisoResponseDTO(piso.getId(), piso.getNome()));
    }

    @GetMapping
    @Operation(summary = "Lista todos os pisos")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de pisos encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "array", implementation = PisoResponseDTO.class),
                examples = @ExampleObject(
                    value = "[ { \"id\": 1, \"nome\": \"1º Andar\" }, { \"id\": 2, \"nome\": \"2º Andar\" } ]"
                )
            )
        )
    })
    public ResponseEntity<List<PisoResponseDTO>> listar() {
        return ResponseEntity.ok(pisoService.listar());
    }
}
