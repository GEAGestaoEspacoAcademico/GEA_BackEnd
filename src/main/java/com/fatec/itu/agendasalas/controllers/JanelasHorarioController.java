package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.fatec.itu.agendasalas.dto.janelasHorario.DatasRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
                .buildAndExpand(janelasHorarioResponseDTO.janelasHorarioId()).toUri();

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

    @Operation(summary = "Lista os horários disponíveis pela data")
    @GetMapping("/disponiveis/{data}")
    public ResponseEntity<List<JanelasHorarioResponseDTO>> getDisponiveis(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
            List<JanelasHorarioResponseDTO> listaDTO = janelasHorarioService.buscarDisponiveisPorDataDTO(data);
            return ResponseEntity.ok(listaDTO);
    }



    @Operation(summary = "Lista os horários disponíveis em várias datas")
    @PostMapping("/disponiveis/datas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "OK",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(
                    value = "[{ \"janelasHorarioId\": 1, \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" }]")))
    })
    public ResponseEntity<List<JanelasHorarioResponseDTO>> getDisponiveisEmVariasDatas(
        @PathVariable Long salaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Lista de datas a serem checadas",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DatasRequestDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"datas\": [\"2026-01-10\", \"2026-01-11\", \"2026-01-12\", \"2026-01-13\", \"2026-01-14\", \"2026-01-15\", \"2026-01-16\", \"2026-01-17\", \"2026-01-18\", \"2026-01-19\"] }")))
        @RequestBody DatasRequestDTO datasRequest) {

        List<JanelasHorarioResponseDTO> listaDTO = janelasHorarioService.buscarDisponiveisEmVariasDatas(datasRequest);
        return ResponseEntity.ok(listaDTO);
    }
}
