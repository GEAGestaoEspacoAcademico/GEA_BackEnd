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

import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoListDTO;
import com.fatec.itu.agendasalas.services.TipoRecursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("tipo-recurso")
@Tag(name = "Tipo de recurso", description = "Operações relacionadas a tipo de recurso")
public class TipoRecursoController {
@Autowired
  private TipoRecursoService tipoRecursoService;

  @Operation(summary = "Lista todos os tipos de recurso existentes")
  @GetMapping
  public ResponseEntity<List<TipoRecursoListDTO>> listar() {
    return ResponseEntity.ok(tipoRecursoService.listarTodos());
  }
  
  @Operation(summary = "Lista um tipo de recurso existente por id")
  @GetMapping("{idTipoRecurso}")
  public ResponseEntity<TipoRecursoListDTO> buscarPorId(@PathVariable Long idTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.buscarPorIdRetornarDTO(idTipoRecurso));
  }

  @Operation(summary = "Cria uma novo tipo de recurso")
  @PostMapping
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<TipoRecursoListDTO> criar(
    @RequestBody TipoRecursoCreateAndUpdateDTO nomeTipoRecurso) {
    TipoRecursoListDTO novoTipoRecurso = tipoRecursoService.criar(nomeTipoRecurso);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTipoRecurso}")
        .buildAndExpand(novoTipoRecurso.id()).toUri();

    return ResponseEntity.created(uri).body(novoTipoRecurso);
  }

  @Operation(summary = "Atualiza um tipo de recurso existente")
  @PutMapping("{idTipoRecurso}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<TipoRecursoListDTO> atualizar(@PathVariable Long idTipoRecurso,
      @RequestBody TipoRecursoCreateAndUpdateDTO nomeTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.atualizar(idTipoRecurso, nomeTipoRecurso));
  }

  @Operation(summary = "Deleta um tipo de recurso existente")
  @DeleteMapping("{idTipoRecurso}")
  //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
  public ResponseEntity<Void> excluir(@PathVariable Long idTipoRecurso) {
    tipoRecursoService.deletar(idTipoRecurso);
    return ResponseEntity.noContent().build();
  } 
}
