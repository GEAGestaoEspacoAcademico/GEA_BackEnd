package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoCreatedDTO;
import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoListDTO;
import com.fatec.itu.agendasalas.services.TipoRecursoService;

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
@RequestMapping("tipo-recurso")
@Tag(name = "Tipo de recurso", description = "Gerenciamento de categorias de recursos (Ex: Eletrônico, Mobiliário)")
public class TipoRecursoController {
@Autowired
  private TipoRecursoService tipoRecursoService;

  @Operation(summary = "Lista todos os tipos de recurso existentes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista retornada",
      content = @Content(mediaType = "application/json",
        schema = @Schema(type = "array", implementation = TipoRecursoListDTO.class),
        examples = @ExampleObject(value = "[ { \"id\": 1, \"nome\": \"Eletrônico\" }, { \"id\": 2, \"nome\": \"Mobiliário\" } ]")))
  })
  @GetMapping
  public ResponseEntity<List<TipoRecursoListDTO>> listar() {
    return ResponseEntity.ok(tipoRecursoService.listarTodos());
  }
  
  @Operation(summary = "Lista um tipo de recurso existente por id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Tipo de recurso encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoRecursoListDTO.class),
            examples = @ExampleObject(value = "{ \"id\": 1, \"nome\": \"Eletrônico\" }"))),
      @ApiResponse(responseCode = "404", description = "Não encontrado")
  })
  @GetMapping("{idTipoRecurso}")
  public ResponseEntity<TipoRecursoListDTO> buscarPorId(
      @Parameter(description = "ID do tipo de recurso") @PathVariable Long idTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.buscarPorIdRetornarDTO(idTipoRecurso));
  }

  @Operation(summary = "Cria um novo tipo de recurso")
  @PostMapping
  public ResponseEntity<TipoRecursoCreatedDTO> criarTipoRecurso(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Nome do tipo de recurso",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = TipoRecursoCreateAndUpdateDTO.class),
              examples = @ExampleObject(value = "{ \"nome\": \"Eletrônico\" }")
          )
      )
      @RequestBody TipoRecursoCreateAndUpdateDTO dto
  ) {
      try {
          long id = tipoRecursoService.criarTipoRecurso(dto);
          return ResponseEntity.status(HttpStatus.CREATED)
                              .body(new TipoRecursoCreatedDTO(id));
      } catch (RuntimeException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
  }


  @Operation(summary = "Atualiza um tipo de recurso existente")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Atualizado com sucesso") })
  @PutMapping("{idTipoRecurso}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<TipoRecursoListDTO> atualizar(
      @Parameter(description = "ID do tipo de recurso") @PathVariable Long idTipoRecurso,
      @RequestBody TipoRecursoCreateAndUpdateDTO nomeTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.atualizar(idTipoRecurso, nomeTipoRecurso));
  }

  @Operation(summary = "Deleta um tipo de recurso existente")
  @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Deletado com sucesso") })
  @DeleteMapping("{idTipoRecurso}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<Void> excluir(@Parameter(description = "ID do tipo de recurso") @PathVariable Long idTipoRecurso) {
    tipoRecursoService.deletar(idTipoRecurso);
    return ResponseEntity.noContent().build();
  } 
}