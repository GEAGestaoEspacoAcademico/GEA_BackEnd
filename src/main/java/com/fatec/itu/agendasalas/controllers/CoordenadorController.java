package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorCreationDTO;
import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorResponseDTO;
import com.fatec.itu.agendasalas.exceptions.RegistroCoordenacaoDuplicadoException;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.services.CoordenadorService;

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
@RequestMapping("/coordenadores")
@Tag(name = "Coordenador", description = "Operações relacionadas a coordenador")
@Validated
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Operation(summary = "Promove um usuário a novo coordenador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Coordenador criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CoordenadorResponseDTO.class),
                examples = @ExampleObject(value = "{ \"coordenadorUsuarioId\": 18, \"coordenadorNome\": \"Paulo Ramos\", \"coordenadorEmail\": \"paulo.ramos@fatec.edu.br\", \"registroCoordenacao\": 2026, \"cargoId\": 4 }"))),
        @ApiResponse(responseCode = "409", description = "Conflito: registro de coordenação já existe")
    })
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> promover(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de um coordenador",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoordenadorCreationDTO.class),
                    examples = @ExampleObject(value = "{ \"coordenadorUsuarioId\": 18, \"registroCoordenacao\": 2026 }")))
            @RequestBody @Validated CoordenadorCreationDTO dto) {
        if (coordenadorRepository.existsByRegistroCoordenacao(dto.registroCoordenacao())) {
            throw new RegistroCoordenacaoDuplicadoException("Registro de coordenação já existe");
        }
        
        return ResponseEntity.created(null).body(coordenadorService.promoverParaCoordenador(dto));
       
    }


    @Operation(summary = "Lista todos os coordenadores existentes")
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CoordenadorResponseDTO>> listar() {
        return ResponseEntity.ok(coordenadorService.listarCoordenadores());
        
    }


    @Operation(summary = "Apresenta um coordenador pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Coordenador encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CoordenadorResponseDTO.class),
                examples = @ExampleObject(value = "{ \"coordenadorUsuarioId\": 18, \"coordenadorNome\": \"Paulo Ramos\", \"coordenadorEmail\": \"paulo.ramos@fatec.edu.br\", \"registroCoordenacao\": 2026, \"cargoId\": 4 }"))),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado")
    })
    @GetMapping("/{coordenadorId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(
        @Parameter(description = "ID do coordenador") @PathVariable Long coordenadorId) {
       
        return ResponseEntity.ok(coordenadorService.buscarPorId(coordenadorId));
    }


    @Operation(summary = "Apresenta um coordenador pelo seu registro de coordenação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Coordenador encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CoordenadorResponseDTO.class),
                examples = @ExampleObject(value = "{ \"coordenadorUsuarioId\": 18, \"coordenadorNome\": \"Paulo Ramos\", \"coordenadorEmail\": \"paulo.ramos@fatec.edu.br\", \"registroCoordenacao\": 2026, \"cargoId\": 4 }"))),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado")
    })
    @GetMapping("/registro/{registro}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorRegistro(
        @Parameter(description = "Registro do coordenador") @PathVariable Long registro) {
            return ResponseEntity.ok(coordenadorService.buscarPorRegistro(registro));
    }


    @Operation(summary = "Despromove (deleta) um coordenador pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Coordenador despromovido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado")
    })
    @DeleteMapping("/{coordenadorId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> despromover(
        @Parameter(description = "ID do coordenador a ser despromovido") @PathVariable Long coordenadorId) {
        coordenadorService.despromoverCoordenador(coordenadorId);
        return ResponseEntity.noContent().build();
    }


}
