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
import com.fatec.itu.agendasalas.dto.RecursoCompletoDTO;
import com.fatec.itu.agendasalas.dto.RecursoResumidoDTO;
import com.fatec.itu.agendasalas.services.RecursoService;

@CrossOrigin
@RestController
@RequestMapping("recursos")
public class RecursoController {
  @Autowired
  private RecursoService recursoService;

  @GetMapping
  public ResponseEntity<List<RecursoCompletoDTO>> listarTodos() {
    return ResponseEntity.ok(recursoService.listarTodosOsRecursos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecursoCompletoDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(recursoService.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<RecursoCompletoDTO> criar(@RequestBody RecursoResumidoDTO dto) {
    RecursoCompletoDTO recursoCriado = recursoService.criar(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(recursoCriado.id()).toUri();
    return ResponseEntity.created(uri).body(recursoCriado);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RecursoCompletoDTO> atualizar(@PathVariable Long id, @RequestBody RecursoResumidoDTO dto) {
    return ResponseEntity.ok(recursoService.atualizar(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    recursoService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
