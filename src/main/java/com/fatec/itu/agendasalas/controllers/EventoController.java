package com.fatec.itu.agendasalas.controllers;

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

import com.fatec.itu.agendasalas.dto.eventosDTO.EventoCreationDTO;
import com.fatec.itu.agendasalas.dto.eventosDTO.EventoResponseDTO;
import com.fatec.itu.agendasalas.services.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("eventos")
@Tag(name = "Evento", description = "Operações relacionadas a eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Operation(summary = "Cria um novo evento")
    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do evento para criar"
                                                                ,required = true, content = @Content(mediaType = "application/json"
                                                                , schema = @Schema(implementation = EventoCreationDTO.class)
                                                                , examples = @ExampleObject(value = "{ \"nomeEvento\": \"Reunião Geral\", \"descricaoEvento\": \"Reunião mensal\" }"))) 
                                                                @Valid @RequestBody EventoCreationDTO novoEvento) {

        EventoResponseDTO criado = eventoService.criar(novoEvento);
        return ResponseEntity.status(201).body(criado);
    }

    @Operation(summary = "Lista todos os eventos")
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarEventos() {
        return ResponseEntity.ok(eventoService.listar());
    }

    @Operation(summary = "Busca um evento pelo id")
    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long eventoId) {
        return ResponseEntity.ok(eventoService.buscarPorId(eventoId));
    }

    @Operation(summary = "Atualiza um evento pelo id")
    @PutMapping("/{eventoId}")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Long eventoId,
            @RequestBody EventoCreationDTO novoEvento) { 

        return ResponseEntity.ok(eventoService.atualizar(eventoId, novoEvento));
    }

    @Operation(summary = "Deleta um evento pelo id")
    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long eventoId) {
        eventoService.excluir(eventoId);
        return ResponseEntity.noContent().build();
    }
}
