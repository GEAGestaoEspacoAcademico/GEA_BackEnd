package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioCreationDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioUpdateDTO;
import com.fatec.itu.agendasalas.services.JanelasHorarioService;



@CrossOrigin
@RestController
@RequestMapping("janelas-horario")
public class JanelasHorarioController {

    @Autowired
    private JanelasHorarioService janelasHorarioService;

    @GetMapping
    public ResponseEntity<List<JanelasHorarioResponseDTO>> listarTodasJanelasHorario(){
        return ResponseEntity.ok(janelasHorarioService.listarTodasJanelasHorario());
    }

    @PostMapping
    public ResponseEntity<JanelasHorarioResponseDTO> criarJanelaHorario(@RequestBody JanelasHorarioCreationDTO janelasHorarioCreationDTO){
        JanelasHorarioResponseDTO janelasHorarioResponseDTO = janelasHorarioService.criarJanelaHorario(janelasHorarioCreationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(janelasHorarioResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(janelasHorarioResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JanelasHorarioResponseDTO> filtrarJanelaHorarioPeloID(@PathVariable Long id){
        return ResponseEntity.ok(janelasHorarioService.filtrarJanelaHorarioPeloID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JanelasHorarioResponseDTO> atualizarJanelasHorario(@PathVariable Long id, @RequestBody JanelasHorarioUpdateDTO janelasHorarioUpdateDTO){
        return ResponseEntity.ok(janelasHorarioService.atualizarJanelasHorario(id, janelasHorarioUpdateDTO));
    }
}
