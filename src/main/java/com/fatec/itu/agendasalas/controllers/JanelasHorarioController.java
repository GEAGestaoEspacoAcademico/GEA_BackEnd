package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.services.JanelasHorarioService;

@CrossOrigin
@RestController
@RequestMapping("janelas-horario")
public class JanelasHorario {

    @Autowired
    private JanelasHorarioService janelasHorarioService;

    @GetMapping
    public ResponseEntity<List<JanelasHorarioResponseDTO>> listarTodasJanelasHorario(){

        return ResponseEntity.ok(janelasHorarioService.listarTodosAgendamentosAula());
    }
}
