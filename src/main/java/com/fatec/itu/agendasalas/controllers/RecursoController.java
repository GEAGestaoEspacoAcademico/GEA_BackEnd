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
import com.fatec.itu.agendasalas.dto.recursos.RecursoResumidoDTO;
import com.fatec.itu.agendasalas.services.RecursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("recursos")
@Tag(name = "Recursos", description = "Operações relacionadas a recurso")
public class RecursoController {
  @Autowired
  private RecursoService recursoService;

  @Operation(summary = "Lista todos os recursos existentes")
  @GetMapping
  public ResponseEntity<List<RecursoCompletoDTO>> listarTodos() {
    return ResponseEntity.ok(recursoService.listarTodosOsRecursos());
  }

  @Operation(summary = "Apresenta um recurso existente pelo seu id")
  @GetMapping("/{recursoId}")
  public ResponseEntity<RecursoCompletoDTO> buscarPorId(@PathVariable Long recursoId) {
    return ResponseEntity.ok(recursoService.buscarPorId(recursoId));
  }

  @Operation(summary = "Cria um novo recurso")
  @PostMapping
  public ResponseEntity<RecursoCompletoDTO> criar(@RequestBody RecursoResumidoDTO recurso) {
    RecursoCompletoDTO recursoCriado = recursoService.criar(recurso);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(recursoCriado.id()).toUri();
    return ResponseEntity.created(uri).body(recursoCriado);
  }

  @Operation(summary = "Atualiza um recurso existente por id")
  @PutMapping("/{recursoId}")
  public ResponseEntity<RecursoCompletoDTO> atualizar(@PathVariable Long recursoId,
      @RequestBody RecursoResumidoDTO recurso) {
    return ResponseEntity.ok(recursoService.atualizar(recursoId, recurso));
  }

  @Operation(summary = "Deleta um recurso existente por id")
  @DeleteMapping("/{recursoId}")
  public ResponseEntity<Void> deletar(@PathVariable Long recursoId) {
    recursoService.deletar(recursoId);
    return ResponseEntity.noContent().build();
  }
}
