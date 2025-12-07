package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoResponseDTO;
import com.fatec.itu.agendasalas.services.AgendamentoEventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("agendamentos/eventos")
@CrossOrigin
@Tag(name = "Agendamento de Evento", description = "Operações relacionadas a agendamento de eventos")
public class AgendamentoEventoController {
   
      @Autowired
      private AgendamentoEventoService agendamentoEventoService;

      /*@PostMapping
      public ResponseEntity<Void> cadastrarAgendamentoEvento(@RequestBody @Valid AgendamentoEventoCreationDTO agendamento){
          agendamentoEventoService.criarAgendamentoEvento(agendamento);
          return ResponseEntity.noContent().build();

      }*/
    

    @Operation(summary = "Cria um novo agendamento de evento")
    @PostMapping
    public ResponseEntity<Void> criar(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados para criação de um agendamento de evento",
        required = true,
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AgendamentoEventoCreationDTO.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                value = "{ \"usuarioId\": 20, \"eventoNome\": \"Fórum de Tecnologia\", \"salaId\": 4, \"dias\": [ { \"dia\": \"2025-12-10\", \"horaInicio\": \"09:00\", \"horaFim\": \"12:00\" }, { \"dia\": \"2025-12-11\", \"horaInicio\": \"14:00\", \"horaFim\": \"17:00\" } ] }")))
        @RequestBody @Valid AgendamentoEventoCreationDTO dto) throws MessagingException {
      
        agendamentoEventoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(summary="Lista todos os agendamentos de evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de agendamentos de evento encontrada",
        content = @Content(mediaType = "application/json",
          schema = @Schema(type = "array", implementation = AgendamentoEventoResponseDTO.class),
          examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
            value = "[ { \"agendamentoEventoId\": 11, \"usuarioNome\": \"Prof. Francisco Bianchi\", \"salaNome\": \"Lab 303\", \"eventoNome\": \"InterFATECS\", \"data\": \"2025-11-15\", \"diaDaSemana\": \"Sexta-feira\", \"horaInicio\": \"09:30\", \"horaFim\": \"11:20\", \"isEvento\": true, \"recorrenciaId\": 2, \"status\": \"ATIVO\", \"solicitante\": \"Sistema\" } ]")))
    })
    @GetMapping
    public ResponseEntity<List<AgendamentoEventoResponseDTO>> listar(){
        return ResponseEntity.ok().body(agendamentoEventoService.listarAgendamentosEvento());
    }
} 


  

