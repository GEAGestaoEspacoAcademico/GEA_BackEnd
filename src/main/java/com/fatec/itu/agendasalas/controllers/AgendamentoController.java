package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoDTO;
import com.fatec.itu.agendasalas.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("agendamentos")
@Tag(name = "Agendamento", description = "Operações relacionadas a agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Lista todos os agendamentos existentes")
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentos(){

        return ResponseEntity.ok(agendamentoService.listarAgendamentos()); 
    }

}
