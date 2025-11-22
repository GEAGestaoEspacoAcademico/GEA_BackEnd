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

import com.fatec.itu.agendasalas.dto.tiposSalas.TipoSalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.tiposSalas.TipoSalaListDTO;
import com.fatec.itu.agendasalas.services.TipoSalaService;

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
@RequestMapping("tipos-salas")
@Tag(name = "Tipo de sala", description = "Gerenciamento de categorias de salas (Ex: Laboratório, Auditório)")
public class TipoSalaController {

  @Autowired
  private TipoSalaService tipoSalaService;

  @Operation(summary = "Lista todos os tipos de sala existentes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista retornada",
      content = @Content(mediaType = "application/json",
        schema = @Schema(type = "array", implementation = TipoSalaListDTO.class),
        examples = @ExampleObject(value = "[ { \"tipoSalaId\": 1, \"tipoSalaNome\": \"Laboratório de Informática\" }, { \"tipoSalaId\": 2, \"tipoSalaNome\": \"Auditório\" } ]")))
  })
  @GetMapping
  public ResponseEntity<List<TipoSalaListDTO>> listar() {
    return ResponseEntity.ok(tipoSalaService.listarTodos());
  }
  
  @Operation(summary = "Lista um tipo sala existente por id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Encontrado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoSalaListDTO.class),
            examples = @ExampleObject(value = "{ \"tipoSalaId\": 1, \"tipoSalaNome\": \"Laboratório de Informática\" }"))),
      @ApiResponse(responseCode = "404", description = "Não encontrado")
  })
  @GetMapping("{idTipoSala}")
  public ResponseEntity<TipoSalaListDTO> buscarPorId(
      @Parameter(description = "ID do tipo de sala") @PathVariable Long idTipoSala) {
    return ResponseEntity.ok(tipoSalaService.buscarPorIdRetornarDTO(idTipoSala));
  }

  @Operation(summary = "Cria uma novo tipo de sala")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Criado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoSalaListDTO.class),
            examples = @ExampleObject(value = "{ \"tipoSalaId\": 3, \"tipoSalaNome\": \"Sala de Aula Padrão\" }")))
  })
  @PostMapping
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<TipoSalaListDTO> criar(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Nome do tipo de sala",
        required = true,
        content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = TipoSalaCreateAndUpdateDTO.class),
          examples = @ExampleObject(value = "{ \"tipoSalaNome\": \"Laboratório de Informática\" }")))
      @RequestBody TipoSalaCreateAndUpdateDTO nomeTipoSala) {
    TipoSalaListDTO novoTipoSala = tipoSalaService.criar(nomeTipoSala);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTipoSala}")
        .buildAndExpand(novoTipoSala.tipoSalaId()).toUri();

    return ResponseEntity.created(uri).body(novoTipoSala);
  }

  @Operation(summary = "Atualiza um tipo de sala existente")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Atualizado com sucesso") })
  @PutMapping("{idTipoSala}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<TipoSalaListDTO> atualizar(
      @Parameter(description = "ID do tipo de sala") @PathVariable Long idTipoSala,
      @RequestBody TipoSalaCreateAndUpdateDTO nomeTipoSala) {
    return ResponseEntity.ok(tipoSalaService.atualizar(idTipoSala, nomeTipoSala));
  }

  @Operation(summary = "Deleta um tipo de sala existente")
  @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Deletado com sucesso") })
  @DeleteMapping("{idTipoSala}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<Void> excluir(@Parameter(description = "ID do tipo de sala") @PathVariable Long idTipoSala) {
    tipoSalaService.deletar(idTipoSala);
    return ResponseEntity.noContent().build();
  }
}