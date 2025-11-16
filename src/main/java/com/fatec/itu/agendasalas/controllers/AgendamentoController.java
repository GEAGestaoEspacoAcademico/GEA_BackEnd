package com.fatec.itu.agendasalas.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDisciplinaDTO;
import com.fatec.itu.agendasalas.services.AgendamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("agendamentos")
@Tag(name = "Agendamento", description = "Operações relacionadas a agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(summary = "Lista todos os agendamentos existentes")
    @GetMapping
    public ResponseEntity<List<AgendamentoNotificacaoDisciplinaDTO>> listarAgendamentos() {
        return ResponseEntity.ok(agendamentoService.listarAgendamentosDisciplina());
    }

    @Operation(summary = "Busca todos os agendamentos correspondentes à data informada")
    @GetMapping("/{data}")
    public ResponseEntity<List<AgendamentoNotificacaoDisciplinaDTO>> buscarAgendamentosPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

                List<AgendamentoNotificacaoDisciplinaDTO> agendamentos = agendamentoService.buscarAgendamentosPorData(data);

                if (agendamentos.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(agendamentos);
    }
}
