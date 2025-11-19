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
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaIndividualCreationDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListaCreationDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListagemRecursosDTO;
import com.fatec.itu.agendasalas.dto.salas.RequisicaoDeSalaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaListDTO;
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
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Lista de salas encontrada",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = SalaListDTO.class),
                    examples = @ExampleObject(value = "[ "
                            + "  { \"id\": 1, \"nome\": \"Sala 101\", \"capacidade\": 30, \"piso\": 1, \"disponibilidade\": true, \"tipoSala\": \"Laboratório de Informática\" }, "
                            + "  { \"id\": 2, \"nome\": \"Sala 205\", \"capacidade\": 25, \"piso\": 2, \"disponibilidade\": false, \"tipoSala\": \"Sala de Aula Comum\" } "
                            + "]"))})})
    public ResponseEntity<List<SalaListDTO>> listarTodas() {
        return ResponseEntity.ok(salaService.listarTodasAsSalas());
    }

    @GetMapping("/{salaId}")
    @Operation(summary = "Apresenta uma sala pelo seu id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Sala encontrada",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SalaDetailDTO.class),
                    examples = @ExampleObject(
                            value = "{ \"id\": 1,\"nome\": \"Sala 101\", \"capacidade\": 10, \"piso\": 2, \"disponibilidade\": false, \"tipoSala\": \"Sala\", \"observacoes\": \"Sala com notebooks\"}"))})})
    public ResponseEntity<SalaDetailDTO> buscarPorId(
            @Parameter(description = "ID da sala a ser buscada") @PathVariable Long salaId) {
        SalaDetailDTO dto = salaService.buscarPorId(salaId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    @Operation(summary = "Cria uma nova sala")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Sala criada com sucesso",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SalaDetailDTO.class),
                    examples = @ExampleObject(
                            value = "{ \"id\": 1,\"nome\": \"Sala 101\", \"capacidade\": 10, \"piso\": 2, \"disponibilidade\": false, \"tipoSala\": \"Sala\", \"observacoes\": \"Sala com notebooks\"}"))})})
    public ResponseEntity<SalaDetailDTO> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sala para ser criada", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaCreateAndUpdateDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"nome\": \"Nova sala\", \"capacidade\": 10, \"piso\": 1, \"disponibilidade\": true, \"idTipoSala\": 1, \"observacoes\": \"Sala em perfeito estado\"}"))) @RequestBody SalaCreateAndUpdateDTO sala) {
        SalaDetailDTO salaCriada = salaService.criar(sala);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(salaCriada.salaId()).toUri();
        return ResponseEntity.created(uri).body(salaCriada);
    }

    @Operation(summary = "Deleta uma sala existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Sala deletada com sucesso", content = @Content)})
    @DeleteMapping("/{salaId}")
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da sala a ser deletada") @PathVariable Long salaId) {
        salaService.deletar(salaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um recurso existente a uma sala existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Recurso adicionado à sala",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecursoSalaListaCreationDTO.class),
                    examples = @ExampleObject(
                            value = "  { \"idRecurso\": 1, \"nome\": \"Projetor Multimídia\", \"tipo\": \"Equipamento Eletrônico\", \"quantidade\": 10 }"))})})
    @PostMapping("/{salaId}/recursos")
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<Void> adicionarRecurso(
            @Parameter(description = "ID da sala") @PathVariable Long salaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ID do recurso e quantidade a ser adicionada", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoSalaIndividualCreationDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"idRecurso\": 1, \"quantidade\": 10 }"))) @RequestBody RecursoSalaListaCreationDTO recurso) {
        
        salaService.adicionarRecurso(salaId, recurso);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os recursos de uma sala existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Recursos da sala listados",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = RecursoSalaListaCreationDTO.class),
                    examples = @ExampleObject(value = "[ "
                            + "  { \"idRecurso\": 1, \"nome\": \"Projetor Multimídia\", \"tipo\": \"Equipamento Eletrônico\", \"quantidade\": 1 }, "
                            + "  { \"idRecurso\": 2, \"nome\": \"Cadeiras\", \"tipo\": \"Mobiliário\", \"quantidade\": 30 }, "
                            + "  { \"idRecurso\": 3, \"nome\": \"Ar Condicionado\", \"tipo\": \"Equipamento Eletrônico\", \"quantidade\": 2 } "
                            + "]"))})})
    @GetMapping("/{salaId}/recursos")
    public ResponseEntity<List<RecursoSalaListagemRecursosDTO>> listarRecursos(
            @Parameter(description = "ID da sala") @PathVariable Long salaId) {
        return ResponseEntity.ok(salaService.listarRecursosPorSala(salaId));
    }

    @Operation(summary = "Remove um recurso de uma sala existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Recurso removido da sala com sucesso", content = @Content)})
    @DeleteMapping("/{salaId}/recursos/{recursoId}")
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<Void> removerRecurso(
            @Parameter(description = "ID da sala") @PathVariable Long salaId,
            @Parameter(description = "ID do recurso a ser removido") @PathVariable Long recursoId) {
        salaService.removerRecurso(salaId, recursoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza a quantidade de um recurso em uma sala")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Quantidade do recurso atualizada com sucesso",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecursoSalaListaCreationDTO.class),
                    examples = @ExampleObject(
                            value = "{ \"idRecurso\": 1, \"nome\": \"Projetor Multimídia\", \"tipo\": \"Equipamento Eletrônico\", \"quantidade\": 5 }"))})})
    @PutMapping("/{salaId}/recursos/{recursoId}")
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<Void> atualizarQuantidadeRecurso(
            @Parameter(description = "ID da sala") @PathVariable Long salaId,
            @Parameter(description = "ID do recurso a ser atualizado") @PathVariable Long recursoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nova quantidade do recurso", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoSalaUpdateQuantidadeDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"quantidade\": 5 }"))) @RequestBody RecursoSalaUpdateQuantidadeDTO quantidade) {
        
        salaService.atualizarQuantidade(salaId, recursoId, quantidade);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza uma sala existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Sala atualizada com sucesso",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SalaDetailDTO.class),
                    examples = @ExampleObject(
                            value = "{ \"nome\": \"Sala 101 - Atualizada\", \"capacidade\": 20, \"piso\": 1, \"disponibilidade\": false, \"idTipoSala\": 1, \"observacoes\": \"Sala em manutenção\"}"))})})
    @PutMapping("/{salaId}")
   // @PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<SalaDetailDTO> atualizarSala(
            @Parameter(description = "ID da sala a ser atualizada") @PathVariable Long salaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da sala para atualizar", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaCreateAndUpdateDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"nome\": \"Sala 101 - Atualizada\", \"capacidade\": 20, \"piso\": 1, \"disponibilidade\": false, \"idTipoSala\": 1, \"observacoes\": \"Sala em manutenção\"}"))) @RequestBody SalaCreateAndUpdateDTO sala) {
        SalaDetailDTO salaAtualizada = salaService.atualizar(salaId, sala);
        return ResponseEntity.ok(salaAtualizada);
    }

    @Operation(summary = "Lista todas as recomendações de sala baseado nos parâmetros passados")
    @PostMapping("/recomendacoes")
    public ResponseEntity<List<SalaListDTO>> recomendacoes(
            @RequestBody @Valid RequisicaoDeSalaDTO requisicao) {
        return ResponseEntity.ok(salaService.recomendacaoDeSala(requisicao));
    }

    @Operation(summary = "Lista as salas disponíveis")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<SalaListDTO>> listarSalasDisponiveis() {
        return ResponseEntity.ok(salaService.listarSalasDisponiveis());
    }
}
