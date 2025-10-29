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

import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaResumidoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaListDTO;
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

  @GetMapping("/{salaId}")
  public ResponseEntity<SalaDetailDTO> buscarPorId(@PathVariable Long salaId) {
    SalaDetailDTO dto = salaService.buscarPorId(salaId);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<SalaDetailDTO> criar(@RequestBody SalaCreateAndUpdateDTO sala) {
    SalaDetailDTO salaCriada = salaService.criar(sala);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(salaCriada.id()).toUri();

    return ResponseEntity.created(uri).body(salaCriada);
  }

  @DeleteMapping("/{salaId}")
  public ResponseEntity<Void> deletar(@PathVariable Long salaId) {
    salaService.deletar(salaId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{salaId}/recursos")
  public ResponseEntity<RecursoSalaCompletoDTO> adicionarRecurso(@PathVariable Long salaId,
      @RequestBody RecursoSalaResumidoDTO recurso) {
    return ResponseEntity.ok(salaService.adicionarRecurso(salaId, recurso));
  }

  @GetMapping("/{salaId}/recursos")
  public ResponseEntity<List<RecursoSalaCompletoDTO>> listarRecursos(@PathVariable Long salaId) {
    return ResponseEntity.ok(salaService.listarRecursosPorSala(salaId));
  }

  @DeleteMapping("/{salaId}/recursos/{recursoId}")
  public ResponseEntity<Void> removerRecurso(@PathVariable Long salaId,
      @PathVariable Long recursoId) {
    salaService.removerRecurso(salaId, recursoId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{salaId}/recursos/{recursoId}")
  public ResponseEntity<RecursoSalaCompletoDTO> atualizarQuantidadeRecurso(
      @PathVariable Long salaId, @PathVariable Long recursoId,
      @RequestBody RecursoSalaUpdateQuantidadeDTO quantidade) {
    return ResponseEntity.ok(salaService.atualizarQuantidade(salaId, recursoId, quantidade));
  }

  @PutMapping("/{salaId}")
  public ResponseEntity<SalaDetailDTO> atualizarSala(@PathVariable Long salaId,
      @RequestBody SalaCreateAndUpdateDTO sala) {
    SalaDetailDTO salaAtualizada = salaService.atualizar(salaId, sala);
    return ResponseEntity.ok(salaAtualizada);
  }
}
