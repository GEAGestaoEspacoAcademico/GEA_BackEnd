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
import com.fatec.itu.agendasalas.dto.RecursoDaSalaDTO;
import com.fatec.itu.agendasalas.dto.RecursoUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.SalaCreateDTO;
import com.fatec.itu.agendasalas.dto.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.SalaListDTO;
import com.fatec.itu.agendasalas.services.SalaService;

@CrossOrigin
@RestController
@RequestMapping("salas")
public class SalaController {

  @Autowired
  private SalaService salaService;

  @GetMapping
  public ResponseEntity<List<SalaListDTO>> listarTodas() {
    return ResponseEntity.ok(salaService.listarTodasAsSalas());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SalaDetailDTO> buscarPorId(@PathVariable Long id) {
    SalaDetailDTO dto = salaService.buscarPorId(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<SalaDetailDTO> criar(@RequestBody SalaCreateDTO dto) {
    SalaDetailDTO salaCriada = salaService.criar(dto);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(salaCriada.id()).toUri();

    return ResponseEntity.created(uri).body(salaCriada);
  }

  @PostMapping("/{salaId}/recursos")
  public ResponseEntity<SalaDetailDTO> adicionarRecurso(@PathVariable Long salaId,
      @RequestBody RecursoDaSalaDTO dto) {
    SalaDetailDTO salaAtualizada = salaService.adicionarRecurso(salaId, dto);
    return ResponseEntity.ok(salaAtualizada);
  }

  @DeleteMapping("/{salaId}/recursos/{recursoId}")
  public ResponseEntity<Void> removerRecurso(@PathVariable Long salaId,
      @PathVariable Long recursoId) {
      salaService.removerRecurso(salaId, recursoId);
      return ResponseEntity.noContent().build();
  }

  @PutMapping("/{salaId}/recursos/{recursoId}")
  public ResponseEntity<SalaDetailDTO> atualizarQuantidadeRecurso(@PathVariable Long salaId,
      @PathVariable Long recursoId, @RequestBody RecursoUpdateQuantidadeDTO dto) {
      SalaDetailDTO salaAtualizada = salaService.atualizarQuantidade(salaId, recursoId, dto);
      return ResponseEntity.ok(salaAtualizada);
  }
}
