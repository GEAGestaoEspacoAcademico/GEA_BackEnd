package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.time.LocalDate;
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
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioPorDataRequestDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioPorVariasDatasRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Lista todas as janelas de horários cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(value = "[ { \"janelasHorarioId\": 1, \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" }, { \"janelasHorarioId\": 2, \"horaInicio\": \"09:30:00\", \"horaFim\": \"11:10:00\" } ]")))
    })
    @GetMapping
    public ResponseEntity<List<JanelasHorarioResponseDTO>> listarTodasJanelasHorario() {
        return ResponseEntity.ok(janelasHorarioService.listarTodasJanelasHorario());
    }

    @Operation(summary = "Cria uma nova janela de horário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Janela criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(value = "{ \"janelasHorarioId\": 3, \"horaInicio\": \"13:30:00\", \"horaFim\": \"15:10:00\" }")))
    })
    @PostMapping
    public ResponseEntity<JanelasHorarioResponseDTO> criarJanelaHorario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados da nova janela de horário",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JanelasHorarioCreationDTO.class),
                    examples = @ExampleObject(value = "{ \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" }")))
            @RequestBody JanelasHorarioCreationDTO janelasHorarioCreationDTO) {
        JanelasHorarioResponseDTO janelasHorarioResponseDTO =
                janelasHorarioService.criarJanelaHorario(janelasHorarioCreationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(janelasHorarioResponseDTO.janelasHorarioId()).toUri();

        return ResponseEntity.created(uri).body(janelasHorarioResponseDTO);
    }

    @Operation(summary = "Busca uma janela de horário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Janela encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(value = "{ \"janelasHorarioId\": 1, \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" }"))),
        @ApiResponse(responseCode = "404", description = "Janela não encontrada")
    })
    @GetMapping("/{janelaHorarioId}")
    public ResponseEntity<JanelasHorarioResponseDTO> filtrarJanelaHorarioPeloID(
            @Parameter(description = "ID da janela") @PathVariable Long janelaHorarioId) {
        return ResponseEntity.ok(janelasHorarioService.filtrarJanelaHorarioPeloID(janelaHorarioId));
    }

    @Operation(summary = "Atualiza uma janela de horário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Janela atualizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(value = "{ \"janelasHorarioId\": 1, \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" }"))),
        @ApiResponse(responseCode = "404", description = "Janela não encontrada")
    })
    @PutMapping("/{janelaHorarioId}")
    public ResponseEntity<JanelasHorarioResponseDTO> atualizarJanelasHorario(
        @Parameter(description = "ID da janela a ser atualizada") @PathVariable Long janelaHorarioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Novos dados da janela",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JanelasHorarioUpdateDTO.class),
                    examples = @ExampleObject(value = "{ \"horaInicio\": \"09:30:00\", \"horaFim\": \"11:10:00\" }")))
            @RequestBody JanelasHorarioUpdateDTO janelasHorarioUpdateDTO) {
        return ResponseEntity.ok(janelasHorarioService.atualizarJanelasHorario(janelaHorarioId,
                janelasHorarioUpdateDTO));
    }

    @Operation(summary = "Lista os horários disponíveis em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de horários disponíveis",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = JanelasHorarioResponseDTO.class),
                examples = @ExampleObject(value = "[ { \"janelasHorarioId\": 1, \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" } ]")))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Data e sala a serem checadas",
        required = true,
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = JanelasHorarioPorDataRequestDTO.class),
            examples = @ExampleObject(value = "{ \"data\": \"2026-01-10\", \"salaId\": 1 }")))
    @PostMapping("/disponiveis")
    public ResponseEntity<List<JanelasHorarioResponseDTO>> getDisponiveis(
        @RequestBody JanelasHorarioPorDataRequestDTO request) {
            LocalDate data = LocalDate.parse(request.data());
            Long salaId = request.salaId();
            List<JanelasHorarioResponseDTO> listaDTO = janelasHorarioService.buscarDisponiveisPorDataDTO(data, salaId);
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
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Lista de datas e sala a serem checadas",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JanelasHorarioPorVariasDatasRequestDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"datas\": [\"2026-01-10\", \"2026-01-11\"], \"salaId\": 1 }")))
        @RequestBody JanelasHorarioPorVariasDatasRequestDTO datasRequest) {

        List<JanelasHorarioResponseDTO> listaDTO = janelasHorarioService.buscarDisponiveisEmVariasDatas(datasRequest);
        return ResponseEntity.ok(listaDTO);
    }
}
