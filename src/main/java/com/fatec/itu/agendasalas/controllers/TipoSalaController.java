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

@CrossOrigin
@RestController
@RequestMapping("tipossalas")
public class TipoSalaController {

  @Autowired
  private TipoSalaService tipoSalaService;

  @GetMapping
  public ResponseEntity<List<TipoSalaListDTO>> listar() {
    return ResponseEntity.ok(tipoSalaService.listarTodos());
  }

  @GetMapping("{idTipoSala}")
  public ResponseEntity<TipoSalaListDTO> buscarPorId(@PathVariable Long idTipoSala) {
    return ResponseEntity.ok(tipoSalaService.buscarPorIdRetornarDTO(idTipoSala));
  }

  @PostMapping
  public ResponseEntity<TipoSalaListDTO> criar(
      @RequestBody TipoSalaCreateAndUpdateDTO nomeTipoSala) {
    TipoSalaListDTO novoTipoSala = tipoSalaService.criar(nomeTipoSala);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTipoSala}")
        .buildAndExpand(novoTipoSala.id()).toUri();

    return ResponseEntity.created(uri).body(novoTipoSala);
  }

  @PutMapping("{idTipoSala}")
  public ResponseEntity<TipoSalaListDTO> atualizar(@PathVariable Long idTipoSala,
      @RequestBody TipoSalaCreateAndUpdateDTO nomeTipoSala) {
    return ResponseEntity.ok(tipoSalaService.atualizar(idTipoSala, nomeTipoSala));
  }

  @DeleteMapping("{idTipoSala}")
  public ResponseEntity<Void> excluir(@PathVariable Long idTipoSala) {
    tipoSalaService.deletar(idTipoSala);
    return ResponseEntity.noContent().build();
  }
}
