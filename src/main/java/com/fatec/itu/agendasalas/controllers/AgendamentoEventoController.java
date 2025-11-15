package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.services.AgendamentoEventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("agendamentos/eventos")
@CrossOrigin
public class AgendamentoEventoController {
   
    @Autowired
    private AgendamentoEventoService agendamentoEventoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarAgendamentoEvento(@RequestBody @Valid AgendamentoEventoCreationDTO agendamento){
        agendamentoEventoService.criarAgendamentoEvento(agendamento);
        return ResponseEntity.noContent().build();

    }
  
}