package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListaCreationDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListagemRecursosDTO;
import com.fatec.itu.agendasalas.dto.salas.RequisicaoDeSalaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.services.SalaService;

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
@RequestMapping("salas")
@Tag(name = "Sala", description = "Operações relacionadas a sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

 
    @GetMapping
    @Operation(summary = "Lista todas as salas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de salas encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = SalaDetailDTO.class),
                examples = @ExampleObject(value = "[ { \"salaId\": 1, \"salaNome\": \"Sala 101\", \"capacidade\": 30, \"piso\": 1, \"disponibilidade\": true, \"tipoSala\": \"Laboratório de Informática\", \"salaObservacoes\": \"Nenhuma\" } ]")))
    })
    public ResponseEntity<List<SalaDetailDTO>> listarTodas() {
        return ResponseEntity.ok(salaService.listarTodasAsSalas());
    }

    @GetMapping("/{salaId}")
    @Operation(summary = "Apresenta uma sala pelo seu id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Sala encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SalaDetailDTO.class),
                examples = @ExampleObject(
                    value = "{ \"salaId\": 1, \"salaNome\": \"Sala 101\", \"capacidade\": 10, \"piso\": 2, \"disponibilidade\": false, \"tipoSala\": \"Sala de Aula\", \"salaObservacoes\": \"Sala com notebooks\" }")))
    })
    public ResponseEntity<SalaDetailDTO> buscarPorId(
            @Parameter(description = "ID da sala a ser buscada") @PathVariable Long salaId) {
        return ResponseEntity.ok(salaService.buscarPorId(salaId));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova sala")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Sala criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SalaDetailDTO.class),
                examples = @ExampleObject(
                    value = "{ \"tipoSalaId\": 1, \"salaNome\": \"Nova Sala\", \"salaCapacidade\": 10, \"piso\": 1, \"disponibilidade\": true, \"salaObservacoes\": \"Sala em perfeito estado\" }")))
    })
    public ResponseEntity<SalaDetailDTO> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Sala para ser criada",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SalaCreateAndUpdateDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"tipoSalaId\": 1, \"salaNome\": \"Nova Sala\", \"salaCapacidade\": 10, \"piso\": 1, \"disponibilidade\": true, \"salaObservacoes\": \"Sala em perfeito estado\" }")))
            @RequestBody SalaCreateAndUpdateDTO sala) {

        SalaDetailDTO salaCriada = salaService.criar(sala);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(salaCriada.salaId()).toUri();

        return ResponseEntity.created(uri).body(salaCriada);
    }

   
    @DeleteMapping("/{salaId}")
    @Operation(summary = "Deleta uma sala existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Sala deletada com sucesso") })
    public ResponseEntity<Void> deletar(@PathVariable Long salaId) {
        salaService.deletar(salaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{salaId}/recursos")
    @Operation(summary = "Adiciona recursos a uma sala existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Recurso(s) adicionados à sala",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RecursoSalaListaCreationDTO.class),
                examples = @ExampleObject(
                    value = "{ \"listaDeRecursosParaAdicionar\": [ { \"recursoId\": 1, \"quantidadeRecurso\": 10 } ] }")))
    })
    public ResponseEntity<Void> adicionarRecurso(
            @Parameter(description = "ID da sala") @PathVariable Long salaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Lista de recursos para adicionar",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecursoSalaListaCreationDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"listaDeRecursosParaAdicionar\": [ { \"recursoId\": 1, \"quantidadeRecurso\": 10, \"tipo\": \"Equipamento Eletrônico\" } ] }")))
            @RequestBody RecursoSalaListaCreationDTO recurso) {

        salaService.adicionarRecurso(salaId, recurso);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{salaId}/recursos")
    @Operation(summary = "Lista todos os recursos de uma sala existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Recursos listados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = RecursoSalaListagemRecursosDTO.class),
                examples = @ExampleObject(
                    value = "[ " +
                    "{ \"idRecurso\": 1, \"nomeRecurso\": \"Projetor Multimídia\", \"tipoRecurso\": \"Equipamento Eletrônico\", \"quantidadeRecurso\": 1 }, " +
                    "{ \"idRecurso\": 2, \"nomeRecurso\": \"Cadeiras\", \"tipoRecurso\": \"Mobiliário\", \"quantidadeRecurso\": 30 } " +
                "]")))
    })
    public ResponseEntity<List<RecursoSalaListagemRecursosDTO>> listarRecursos(@PathVariable Long salaId) {
        return ResponseEntity.ok(salaService.listarRecursosPorSala(salaId));
    }

    @DeleteMapping("/{salaId}/recursos/{recursoId}")
    @Operation(summary = "Remove um recurso de uma sala existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Recurso removido") })
    public ResponseEntity<Void> removerRecurso(@PathVariable Long salaId, @PathVariable Long recursoId) {
        salaService.removerRecurso(salaId, recursoId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{salaId}/recursos/{recursoId}")
    @Operation(summary = "Atualiza a quantidade de um recurso em uma sala")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Quantidade atualizada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RecursoSalaUpdateQuantidadeDTO.class),
                examples = @ExampleObject(value = "{ \"quantidade\": 5 }")))
    })
    public ResponseEntity<Void> atualizarQuantidadeRecurso(
            @PathVariable Long salaId,
            @PathVariable Long recursoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nova quantidade",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecursoSalaUpdateQuantidadeDTO.class),
                    examples = @ExampleObject(value = "{ \"quantidade\": 5 }")))
            @RequestBody RecursoSalaUpdateQuantidadeDTO quantidade) {

        salaService.atualizarQuantidade(salaId, recursoId, quantidade);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{salaId}")
    @Operation(summary = "Atualiza uma sala existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Sala atualizada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SalaDetailDTO.class),
                examples = @ExampleObject(
                    value = "{ \"tipoSalaId\": 1, \"salaNome\": \"Sala 101 - Atualizada\", \"salaCapacidade\": 20, \"piso\": 1, \"disponibilidade\": false, \"salaObservacoes\": \"Sala em manutenção\" }")))
    })
    public ResponseEntity<SalaDetailDTO> atualizarSala(
            @PathVariable Long salaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados da sala",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SalaCreateAndUpdateDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"tipoSalaId\": 1, \"salaNome\": \"Sala 101 - Atualizada\", \"salaCapacidade\": 20, \"piso\": 1, \"disponibilidade\": false, \"salaObservacoes\": \"Sala em manutenção\" }")))
            @RequestBody SalaCreateAndUpdateDTO sala) {

        return ResponseEntity.ok(salaService.atualizar(salaId, sala));
    }

    @PostMapping("/recomendacoes")
    @Operation(summary = "Lista recomendações de sala baseado nos parâmetros passados")
    public ResponseEntity<List<SalaDetailDTO>> recomendacoes(
            @RequestBody @Valid RequisicaoDeSalaDTO requisicao) {

        return ResponseEntity.ok(salaService.recomendacaoDeSala(requisicao));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Lista as salas disponíveis")
    public ResponseEntity<List<SalaDetailDTO>> listarSalasDisponiveis() {
        return ResponseEntity.ok(salaService.listarSalasDisponiveis());
    }
}