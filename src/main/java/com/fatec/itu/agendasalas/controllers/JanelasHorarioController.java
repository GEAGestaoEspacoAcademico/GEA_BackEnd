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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@CrossOrigin
@RestController
@RequestMapping("janelas-horario")
@Tag(name = "Janelas de Horário", description = "Operações relacionadas a janelas de horário")
public class JanelasHorarioController {

    @Autowired
    private JanelasHorarioService janelasHorarioService;

    @Operation(summary = "Lista todas as janelas de horários")
    @GetMapping
    public ResponseEntity<List<JanelasHorarioResponseDTO>> listarTodasJanelasHorario() {
        return ResponseEntity.ok(janelasHorarioService.listarTodasJanelasHorario());
    }

    @Operation(summary = "Cria uma nova janela de horário")
    @PostMapping
    public ResponseEntity<JanelasHorarioResponseDTO> criarJanelaHorario(
            @RequestBody JanelasHorarioCreationDTO janelasHorarioCreationDTO) {
        JanelasHorarioResponseDTO janelasHorarioResponseDTO =
                janelasHorarioService.criarJanelaHorario(janelasHorarioCreationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(janelasHorarioResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(janelasHorarioResponseDTO);
    }

    @Operation(summary = "Lista uma janela de horário existente por id")
    @GetMapping("/{janelaHorarioId}")
    public ResponseEntity<JanelasHorarioResponseDTO> filtrarJanelaHorarioPeloID(
            @PathVariable Long janelaHorarioId) {
        return ResponseEntity.ok(janelasHorarioService.filtrarJanelaHorarioPeloID(janelaHorarioId));
    }

    @Operation(summary = "Atualiza uma janela de horário por id")
    @PutMapping("/{janelaHorarioId}")
    public ResponseEntity<JanelasHorarioResponseDTO> atualizarJanelasHorario(
            @PathVariable Long janelaHorarioId,
            @RequestBody JanelasHorarioUpdateDTO janelasHorarioUpdateDTO) {
        return ResponseEntity.ok(janelasHorarioService.atualizarJanelasHorario(janelaHorarioId,
                janelasHorarioUpdateDTO));
    }
}
