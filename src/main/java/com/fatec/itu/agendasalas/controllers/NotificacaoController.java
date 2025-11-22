package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.notificações.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.notificações.NotificacaoResponseDTO;
import com.fatec.itu.agendasalas.services.NotificacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("notificacoes")
@Tag(name = "Notificação", description = "Operações relacionadas a notificação")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Operation(summary = "Lista todas as notificações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacaoResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { \"notificacaoId\": 301, \"notificacaoTitulo\": \"Alteração de horário\", \"notificacaoMensagem\": \"A aula de Banco de Dados foi remarcada para 2025-12-01 às 07:40\", \"dataEnvio\": \"2025-11-22\", \"usuarioRemetente\": { \"usuarioId\": 12, \"usuarioNome\": \"Lucas Silva\" }, \"destinatarios\": [ { \"usuarioId\": 15, \"usuarioNome\": \"Marcia Oliveira\" } ], \"agendamento\": { \"agendamentoId\": 201, \"sala\": { \"salaId\": 5, \"salaNome\": \"Lab 301\" }, \"data\": \"2025-12-01\", \"horaInicio\": \"07:40:00\", \"horaFim\": \"09:20:00\" } } ]")))
    })
    @GetMapping
    public List<NotificacaoResponseDTO> listarNotificacoes() {
        return notificacaoService.listarNotificacoesComoDTO();
    }
    
    @PostMapping
    @Operation(summary = "Criar uma nova notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificações enviadas")
    })
    public void enviarNotificacoes(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Lista de notificações a serem enviadas",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotificacaoCreationDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { \"agendamento\": 201, \"notificacaoTitulo\": \"Lembrete de Sala\", \"notificacaoMensagem\": \"Aula amanhã às 07:40\", \"usuarioRemetente\": 12, \"destinatarios\": [15, 16] } ]")))
        @RequestBody List<NotificacaoCreationDTO> notificacoesDTO) {
        notificacaoService.enviarNotificacoes(notificacoesDTO);
    }
}
