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

@CrossOrigin
@RestController
@RequestMapping("tiporecurso")
public class TipoRecursoController {
@Autowired
  private TipoRecursoService tipoRecursoService;

  @GetMapping
  public ResponseEntity<List<TipoRecursoListDTO>> listar() {
    return ResponseEntity.ok(tipoRecursoService.listarTodos());
  }

  @GetMapping("{idTipoRecurso}")
  public ResponseEntity<TipoRecursoListDTO> buscarPorId(@PathVariable Long idTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.buscarPorIdRetornarDTO(idTipoRecurso));
  }

  @PostMapping
  public ResponseEntity<TipoRecursoListDTO> criar(
    @RequestBody TipoRecursoCreateAndUpdateDTO nomeTipoRecurso) {
    TipoRecursoListDTO novoTipoRecurso = tipoRecursoService.criar(nomeTipoRecurso);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTipoRecurso}")
        .buildAndExpand(novoTipoRecurso.id()).toUri();

    return ResponseEntity.created(uri).body(novoTipoRecurso);
  }

  @PutMapping("{idTipoRecurso}")
  public ResponseEntity<TipoRecursoListDTO> atualizar(@PathVariable Long idTipoRecurso,
      @RequestBody TipoRecursoCreateAndUpdateDTO nomeTipoRecurso) {
    return ResponseEntity.ok(tipoRecursoService.atualizar(idTipoRecurso, nomeTipoRecurso));
  }

  @DeleteMapping("{idTipoRecurso}")
  public ResponseEntity<Void> excluir(@PathVariable Long idTipoRecurso) {
    tipoRecursoService.deletar(idTipoRecurso);
    return ResponseEntity.noContent().build();
  } 
}
