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

import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaCreateDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.services.DisciplinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("disciplinas")
@Tag(name = "Disciplina", description = "Operações relacionadas a disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Operation(summary = "Cria uma nova disciplina")
    @PostMapping
    public ResponseEntity<DisciplinaListDTO> criarDisciplina(@RequestBody DisciplinaCreateDTO novaDisciplina) {
         DisciplinaListDTO disciplinaListResponseDTO = disciplinaService.criar(novaDisciplina);
                
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(disciplinaListResponseDTO.disciplinaId()).toUri();
        
        return ResponseEntity.created(uri).body(disciplinaListResponseDTO);
    }

    @Operation(summary = "Lista todas as disciplinas existentes")
    @GetMapping
    public ResponseEntity<List<DisciplinaListDTO>> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaService.listar());
    }

    @Operation(summary = "Apresenta uma disciplina existente por id")
    @GetMapping("{disciplinaId}")
    public ResponseEntity<DisciplinaListDTO> buscarPorId(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(disciplinaId));
    }

    @Operation(summary = "Atualiza uma disciplina pelo id")
    @PutMapping("{disciplinaId}")
    public ResponseEntity<DisciplinaListDTO> editarDisciplina(@PathVariable Long disciplinaId,
            @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return ResponseEntity.ok(disciplinaService.atualizar(disciplinaId, novaDisciplina));
    }

    @Operation(summary = "Deleta uma disciplina pelo id")
    @DeleteMapping("{disciplinaId}")
    public ResponseEntity<Void> excluirDisciplina(@PathVariable Long disciplinaId) {
        disciplinaService.excluir(disciplinaId);
        return ResponseEntity.noContent().build();
    }
}
