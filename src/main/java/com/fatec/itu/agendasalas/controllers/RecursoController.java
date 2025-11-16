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
import com.fatec.itu.agendasalas.dto.recursos.RecursoCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoResponseDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoResumidoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaCompletoDTO;
import com.fatec.itu.agendasalas.services.RecursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin
@RestController
@RequestMapping("recursos")
@Tag(name = "Recursos", description = "Operações relacionadas a recurso")
public class RecursoController {
  @Autowired
  private RecursoService recursoService;

  @Operation(summary = "Lista todos os recursos existentes")
  @GetMapping
  public ResponseEntity<List<RecursoResponseDTO>> listarTodos() {
    return ResponseEntity.ok(recursoService.listarTodosOsRecursos());
  }

  @Operation(summary = "Apresenta um recurso existente pelo seu id")
  @GetMapping("/{recursoId}")
  public ResponseEntity<RecursoResponseDTO> buscarPorId(@PathVariable Long recursoId) {
    return ResponseEntity.ok(recursoService.buscarPorId(recursoId));
  }

  @Operation(summary = "Cria um novo recurso")
  @PostMapping
  public ResponseEntity<RecursoCompletoDTO> criar(@RequestBody RecursoResumidoDTO recurso) {
    RecursoCompletoDTO recursoCriado = recursoService.criar(recurso);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(recursoCriado.recursoId()).toUri();
    return ResponseEntity.created(uri).body(recursoCriado);
  }

  @Operation(summary = "Atualiza um recurso existente por id")
  @PutMapping("/{recursoId}")
  public ResponseEntity<RecursoCompletoDTO> atualizar(@PathVariable Long recursoId,
      @RequestBody RecursoResumidoDTO recursoDTO) {
    return ResponseEntity.ok(recursoService.atualizar(recursoId, recursoDTO));
  }

  @Operation(summary = "Deleta um recurso existente por id")
  @DeleteMapping("/{recursoId}")
  public ResponseEntity<Void> deletar(@PathVariable Long recursoId) {
    recursoService.deletar(recursoId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Apresenta os recursos existentes de um determinado tipo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Recursos encontrados",
      content = {@Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = RecursoSalaCompletoDTO.class)),
        examples = @ExampleObject(value = "{ \"recursoId\": 5, \"recursoNome\": \"Licença Microsoft Project\", \"recursoTipoId\": 2, \"quantidadeRecurso\": 40 }") )}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
        content = {@Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"message\": \"Nenhum recurso encontrado para o tipo de ID: 8\" }") )})})
  @GetMapping("/tipo/{tipoId}")
  public ResponseEntity<List<RecursoSalaCompletoDTO>> buscarPorTipo(
      @Parameter(description = "ID do tipo de recurso a ser usado como filtro") @PathVariable Long tipoId) {
    List<RecursoSalaCompletoDTO> recursos = recursoService.listarPorTipo(tipoId);
    return ResponseEntity.ok(recursos);
  }
}
