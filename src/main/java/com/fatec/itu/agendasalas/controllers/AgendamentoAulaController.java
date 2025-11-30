package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationByAuxiliarDocenteDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationComRecorrenciaDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.services.AgendamentoAulaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("agendamentos/aulas")
@Tag(name = "Agendamento de Aula", description = "Operações relacionadas a agendamento de aulas")
public class AgendamentoAulaController {

    @Autowired
    private AgendamentoAulaService agendamentoAulaService;

    @PostMapping
    @Operation(summary = "Cria um novo agendamento de aula")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Agendamento feito com sucesso"),
        @ApiResponse(responseCode = "409",
            description = "Conflito: horário indisponível"),
        @ApiResponse(responseCode = "400",
            description = "Requisição inválida")
    })
    public ResponseEntity<Void> criarAgendamentoAula(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados para criação de um agendamento de aula",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoAulaCreationDTO.class),
                examples = @ExampleObject(value = "{ \"usuarioId\": 12, \"salaId\": 5, \"disciplinaId\": 3, \"quantidade\": 30, \"data\": \"2025-11-25\", \"janelasHorarioId\": 2, \"isEvento\": false }")))
        @RequestBody @Valid AgendamentoAulaCreationDTO dto) {
        
            agendamentoAulaService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
     
    }

    @Operation(summary="Cria um novo agendamento de aula em um dia específico")
    @PostMapping("/auxiliar-docente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agendamento(s) criado(s) com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "[{ \"agendamentoAulaId\": 16, \"usuarioNome\": \"Auxiliar Docente\", \"salaNome\": \"Lab 305\", \"disciplinaId\": 3, \"disciplinaNome\": \"Programação Orientada à objetos\", \"semestre\": \"2025.1\", \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"professorNome\": \"Prof. Dimas Cardoso\", \"data\": \"2026-11-23\", \"diaDaSemana\": \"Segunda-feira\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"isEvento\": false }]")))
    })
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> criarAgendamentoAulaByAD(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de agendamento por auxiliar docente",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AgendamentoAulaCreationByAuxiliarDocenteDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"usuarioId\": 4, \"salaId\": 5, \"disciplinaId\": 3, \"data\": \"2026-11-23\", \"horaInicio\": \"07:20\", \"horaFim\": \"09:30\", \"solicitante\": \"Sergio Salgado\" }")))
            @Valid @RequestBody AgendamentoAulaCreationByAuxiliarDocenteDTO dto){
        return ResponseEntity.created(null).body(agendamentoAulaService.criarAgendamentoAulaByAD(dto));
    }


    @Operation(summary = "Cria agendamentos de aula recorrentes entre duas datas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Created",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(
                    value = "[{ \"agendamentoAulaId\": 16, \"usuarioNome\": \"Prof. Fabricio Londero\", \"salaId\": 6, \"salaNome\": \"Lab 306\", \"disciplinaId\": 2, \"disciplinaNome\": \"Laboratório de Banco de Dados\", \"semestre\": \"2025.2\", \"cursoNome\": \"Análise e Desenvolvimento de Sistemas\", \"professorNome\": \"Prof. Fabricio Londero\", \"data\": \"2026-02-02\", \"diaDaSemana\": \"Segunda-feira\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"isEvento\": false }]")))

    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do agendamento",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AgendamentoAulaCreationComRecorrenciaDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"usuarioId\": 7, \"dataInicio\": \"2026-02-02\", \"dataFim\": \"2026-06-06\", \"diaDaSemana\": \"SEGUNDA\", \"janelasHorarioId\": [1,2,3,4], \"disciplinaId\": 2, \"salaId\": 6 }")))
    @PostMapping("/recorrencia")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> criarAgendamentoAulaComRecorrencia(@Valid @RequestBody AgendamentoAulaCreationComRecorrenciaDTO dto) {
        
            List<AgendamentoAulaResponseDTO> agendamentosCriados = agendamentoAulaService.criarAgendamentoAulaComRecorrencia(dto);
            return ResponseEntity.created(null).body(agendamentosCriados);
       
            
        
           
        
    }

    @Operation(summary = "Lista todos os agendamentos de aula")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de agendamentos de aula encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "[ {\"agendamentoAulaId\":1,\"usuarioNome\":\"Lucas Silva\",\"salaNome\":\"Lab 301\",\"disciplinaId\":1,\"disciplinaNome\":\"Engenharia de Software III\",\"semestre\":\"2025.2\",\"cursoNome\":\"Análise e Desenvolvimento de Sistemas\",\"professorNome\":\"Prof. Sergio Salgado\",\"data\":\"2025-12-15\",\"diaDaSemana\":\"Segunda-feira\",\"horaInicio\":\"07:40:00\",\"horaFim\":\"09:20:00\",\"isEvento\":false} ]")))
    })
    @GetMapping
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> listarTodosAgendamentosAula() {
        List<AgendamentoAulaResponseDTO> agendamentos = agendamentoAulaService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Apresenta um agendamento de aula pelo seu id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "{ \"agendamentoAulaId\": 101, \"usuarioNome\": \"Mariana Costa\", \"salaNome\": \"Lab 301\", \"disciplinaId\": 5, \"disciplinaNome\": \"Banco de Dados\", \"semestre\": \"2025.2\", \"cursoNome\": \"Ciência da Computação\", \"professorNome\": \"Prof. João Pedro\", \"data\": \"2025-12-01\", \"diaDaSemana\": \"Segunda-feira\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"isEvento\": false }"))),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{agendamentoAulaId}")
    public ResponseEntity<AgendamentoAulaResponseDTO> buscarAgendamentoAulaPorId(
           @Parameter(description = "ID do agendamento de aula") @PathVariable Long agendamentoAulaId) {
        try {
            AgendamentoAulaResponseDTO response =
                    agendamentoAulaService.buscarPorId(agendamentoAulaId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Lista todos os agendamentos de aula por disciplina")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamentos por disciplina encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "[ { \"agendamentoAulaId\": 102, \"usuarioNome\": \"Carlos Menezes\", \"salaNome\": \"Sala 204\", \"disciplinaId\": 7, \"disciplinaNome\": \"Redes de Computadores\", \"semestre\": \"2025.2\", \"cursoNome\": \"Sistemas de Informação\", \"professorNome\": \"Prof. Maria Clara\", \"data\": \"2025-11-30\", \"diaDaSemana\": \"Sábado\", \"horaInicio\": \"13:30:00\", \"horaFim\": \"15:10:00\", \"isEvento\": false } ]")))
    })
    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorDisciplina(
            @Parameter(description = "ID da disciplina") @PathVariable Integer disciplinaId) {
        List<AgendamentoAulaResponseDTO> agendamentos =
                agendamentoAulaService.buscarPorDisciplina(disciplinaId);
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Lista todos os agendamentos de aula por professor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamentos por professor encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "[ { \"agendamentoAulaId\": 103, \"usuarioNome\": \"Beatriz Souza\", \"salaNome\": \"Lab 102\", \"disciplinaId\": 4, \"disciplinaNome\": \"Programação Orientada a Objetos\", \"semestre\": \"2025.2\", \"cursoNome\": \"Engenharia de Software\", \"professorNome\": \"Prof. Roberto Lima\", \"data\": \"2025-12-02\", \"diaDaSemana\": \"Terça-feira\", \"horaInicio\": \"09:30:00\", \"horaFim\": \"11:10:00\", \"isEvento\": false } ]")))
    })
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> buscarAgendamentosPorProfessor(
            @Parameter(description = "ID do professor (usuário)") @PathVariable Integer professorId) {
        List<AgendamentoAulaResponseDTO> agendamentos =
                agendamentoAulaService.buscarPorProfessor(professorId);
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Atualiza um agendamento de aula pelo seu id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento atualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendamentoAulaResponseDTO.class),
                examples = @ExampleObject(value = "{ \"agendamentoAulaId\": 101, \"usuarioNome\": \"Mariana Costa\", \"salaNome\": \"Lab 301\", \"disciplinaId\": 5, \"disciplinaNome\": \"Banco de Dados\", \"semestre\": \"2025.2\", \"cursoNome\": \"Ciência da Computação\", \"professorNome\": \"Prof. João Pedro\", \"data\": \"2025-12-01\", \"diaDaSemana\": \"Segunda-feira\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\", \"isEvento\": false }"))),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @PutMapping("/{agendamentoAulaId}")
    public ResponseEntity<AgendamentoAulaResponseDTO> atualizarAgendamentoAula(
        @Parameter(description = "ID do agendamento a ser atualizado") @PathVariable Long agendamentoAulaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Novos dados para o agendamento de aula",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AgendamentoAulaCreationDTO.class),
                    examples = @ExampleObject(value = "{ \"usuarioId\": 12, \"salaId\": 5, \"disciplinaId\": 3, \"quantidade\": 30, \"data\": \"2025-11-25\", \"janelasHorarioId\": 2, \"isEvento\": false }")))
        @RequestBody AgendamentoAulaCreationDTO dto) {
        try {
            AgendamentoAulaResponseDTO response =
                    agendamentoAulaService.atualizarAgendamentoAula(agendamentoAulaId, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deleta um agendamento de aula pelo seu id")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado") })
    @DeleteMapping("/{agendamentoAulaId}")
    public ResponseEntity<Void> excluirAgendamentoAula(
        @Parameter(description = "ID do agendamento a ser excluído") @PathVariable Long agendamentoAulaId) {
        try {
            agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
