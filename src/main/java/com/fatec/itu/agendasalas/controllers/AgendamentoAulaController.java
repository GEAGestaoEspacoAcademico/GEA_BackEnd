package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationByAuxiliarDocenteDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationComRecorrenciaDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaFilterDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoCreatedResponseDTO;
import com.fatec.itu.agendasalas.dto.paginacaoDTO.PageableResponseDTO;
import com.fatec.itu.agendasalas.exceptions.DataInicialMaiorQueDataFinalException;
import com.fatec.itu.agendasalas.services.AgendamentoAulaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
            description = "Agendamento criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoCreatedResponseDTO.class))),
        @ApiResponse(responseCode = "409",
            description = "Conflito: horário indisponível"),
        @ApiResponse(responseCode = "400",
            description = "Requisição inválida")
    })
    public ResponseEntity<AgendamentoCreatedResponseDTO> criarAgendamentoAula(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados para criação de um agendamento de aula",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AgendamentoAulaCreationDTO.class),
                examples = @ExampleObject(value = "{ \"usuarioId\": 12, \"salaId\": 5, \"disciplinaId\": 3, \"quantidade\": 30, \"data\": \"2025-11-25\", \"janelasHorarioId\": 2, \"isEvento\": false }")))
        @RequestBody @Valid AgendamentoAulaCreationDTO dto) {
    
        Long idCriado = agendamentoAulaService.criar(dto);

        URI location = URI.create("/agendamentos/aulas/" + idCriado);

        AgendamentoCreatedResponseDTO responseBody =
            new AgendamentoCreatedResponseDTO(idCriado);

        return ResponseEntity
                .created(location)
                .body(responseBody);
    }   

    @Operation(summary = "Cria um novo agendamento de aula em um dia específico")
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
            @Valid @RequestBody AgendamentoAulaCreationByAuxiliarDocenteDTO dto) {
        List<AgendamentoAulaResponseDTO> novosAgendamentos = agendamentoAulaService.criarAgendamentoAulaByAD(dto);
        if (novosAgendamentos == null || novosAgendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Long idPrimeiroAgendamento = novosAgendamentos.get(0).agendamentoAulaId();
        URI location = URI.create("/agendamentos/aulas/" + idPrimeiroAgendamento);
        return ResponseEntity.created(location).body(novosAgendamentos);
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
    public ResponseEntity<List<AgendamentoAulaResponseDTO>> criarAgendamentoAulaComRecorrencia(@Valid @RequestBody AgendamentoAulaCreationComRecorrenciaDTO dto) throws MessagingException {
  
        List<AgendamentoAulaResponseDTO> agendamentosCriados = agendamentoAulaService.criarAgendamentoAulaComRecorrencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentosCriados);
       
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

            return ResponseEntity.ok(agendamentoAulaService.buscarPorId(agendamentoAulaId));
      
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
       
            return ResponseEntity.ok(agendamentoAulaService.atualizarAgendamentoAula(agendamentoAulaId, dto));
        
    }

    @Operation(summary = "Deleta um agendamento de aula pelo seu id")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado") })
    @DeleteMapping("/{agendamentoAulaId}")
    public ResponseEntity<Void> excluirAgendamentoAula(
        @Parameter(description = "ID do agendamento a ser excluído") @PathVariable Long agendamentoAulaId) {
     
            agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaId);
            return ResponseEntity.noContent().build();
    }

        @PostMapping("/busca")
        @Operation(
            summary = "Busca agendamentos de aula filtrando por usuário, sala, disciplina, datas e horários.",
            description = """
                Realiza a busca paginada de agendamentos de aulas usando múltiplos filtros.
                Todos os campos de lista são obrigatórios e não podem ser vazios.
                Caso a data inicial seja maior que a data final, retorna erro 400.
            """
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Busca realizada com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageableResponseDTO.class),
                    examples = @ExampleObject(
                        name="Busca realizada com sucesso",
                        value="""
                        {
                        "conteudo": [
                                        {
                                            "agendamentoAulaId": 1,
                                            "usuarioNome": "Lucas Silva",
                                            "salaId": 1,
                                            "salaNome": "Lab 301",
                                            "disciplinaId": 1,
                                            "disciplinaNome": "Engenharia de Software III",
                                            "semestre": "2025.2",
                                            "cursoNome": "Análise e Desenvolvimento de Sistemas",
                                            "professorNome": "Prof. Sergio Salgado",
                                            "data": "2025-12-15",
                                            "diaDaSemana": "Segunda-feira",
                                            "janelaHorarioId": 1,
                                            "horaInicio": "07:40:00",
                                            "horaFim": "09:20:00",
                                            "isEvento": false
                                        }
                                ],
                        "numeroDaPagina": 0,
                        "tamanhoDaPagina": 10,
                        "totalDeElementos": 1,
                        "totalDePaginas": 1,
                        "ultimaPagina": true
                        }
                            """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Erro de validação ou intervalo de datas inválido",
                content = @Content(
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "Lista vazia",
                            value = """
                            {
                                "status": 400,
                                "error": "Bad Request",
                                "message": "A lista de IDs nao pode ser vazia.",
                                "path": "/agendamentos/aulas/busca"
                            }
                            """
                        ),
                        @ExampleObject(
                            name = "Data inicial maior que data final",
                            value = """
                            {
                                "status": 400,
                                "error": "Bad Request",
                                "message": "Intervalo de datas inválido: dataInicial (20-12-2025) é maior que dataFinal (10-12-2025)",
                                "path": "/agendamentos/aulas/busca"
                            }
                            """
                        )
                    }
                )
            )
        })
        public ResponseEntity<PageableResponseDTO<AgendamentoAulaResponseDTO>> buscaAgendamentosAulaComFiltros(
            @RequestBody @Valid  AgendamentoAulaFilterDTO filtros,

            @Parameter(description = "Número da página", example = "1")
            @RequestParam(required=false, defaultValue = "1") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(required=false, defaultValue = "10") int limit,

            @Parameter(description = "Campo para ordenação", example = "data")
            @RequestParam(required = false, defaultValue="data") String sort
        ){
            if(filtros.dataInicio()!=null && filtros.dataFim()!=null){
                if(filtros.dataInicio().isAfter(filtros.dataFim())){
                    throw new DataInicialMaiorQueDataFinalException(filtros.dataInicio(), filtros.dataFim());
                }
            }

            return ResponseEntity.ok(PageableResponseDTO.fromPage(agendamentoAulaService.listarDisciplinasPorFiltro(filtros, page, limit, sort)));
        }
}
