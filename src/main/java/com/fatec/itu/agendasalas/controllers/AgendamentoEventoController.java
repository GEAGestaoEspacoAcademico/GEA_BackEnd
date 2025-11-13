package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.services.AgendamentoEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("agendamentos/eventos")
@Tag(name = "Agendamento")
public class AgendamentoEventoController {

  @Autowired
  private AgendamentoEventoService agendamentoEventoService;

  @Operation(summary = "Cria um novo agendamento de evento")
  @PostMapping
  public ResponseEntity<Void> criar(
      @RequestBody @Valid AgendamentoEventoCreationDTO dto) {
    try {
      agendamentoEventoService.criar(dto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
