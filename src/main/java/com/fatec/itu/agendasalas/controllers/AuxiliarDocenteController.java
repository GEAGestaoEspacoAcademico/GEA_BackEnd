package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteCreationDTO;
import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.dto.paginacaoDTO.PageableResponseDTO;
import com.fatec.itu.agendasalas.services.AuxiliarDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@Tag(name = "Auxiliar Docente", description = "Operações relacionadas ao cadastro de auxiliares docentes")
@RequestMapping("auxiliar-docentes")
public class AuxiliarDocenteController {

    @Autowired
    private AuxiliarDocenteService auxiliarDocenteService;

    @Operation(summary = "Lista todos os auxiliares docentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de auxiliares docentes encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PageableResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ \"conteudo\": [ { \"id\": 7, \"nome\": \"Marcia Oliveira\", \"email\": \"marcia.oliveira@fatec.edu.br\", \"area\": \"Laboratórios\" } ], \"numeroDaPagina\": 0, \"tamanhoDaPagina\": 10, \"totalDeElementos\": 1, \"totalDePaginas\": 1, \"ultimaPagina\": true }")))
    })
    @GetMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<PageableResponseDTO<AuxiliarDocenteResponseDTO>> listarAuxiliaresDocentes(
        @Parameter(description = "Número da página (0..N)") @RequestParam (defaultValue="0") int page,
        @Parameter(description = "Tamanho da página") @RequestParam(defaultValue="10") int size){
        Page<AuxiliarDocenteResponseDTO> paginacaoAuxiliarDocentes = auxiliarDocenteService.listarAuxiliaresDocentes(page, size);

        return ResponseEntity.ok(PageableResponseDTO.fromPage(paginacaoAuxiliarDocentes));

    }
    

    @Operation(summary = "Cadastra um novo auxiliar docente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Auxiliar docente cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AuxiliarDocenteResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ \"id\": 7, \"nome\": \"Marcia Oliveira\", \"email\": \"marcia.oliveira@fatec.edu.br\", \"area\": \"Laboratórios\" }"))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<AuxiliarDocenteResponseDTO> cadastrarAuxiliarDocente(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de um auxiliar docente",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuxiliarDocenteCreationDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "{ \"login\": \"marcia.oliveira\", \"nome\": \"Marcia Oliveira\", \"email\": \"marcia.oliveira@fatec.edu.br\", \"senha\": \"SenhaSegura2025!\", \"area\": \"Laboratórios\" }")))
            @Valid @RequestBody AuxiliarDocenteCreationDTO auxiliarDocenteCreationDTO){
        return ResponseEntity.created(null).body(auxiliarDocenteService.cadastrarUsuario(auxiliarDocenteCreationDTO));

    } 

}
