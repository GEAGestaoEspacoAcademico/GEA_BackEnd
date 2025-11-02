package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.services.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.fatec.itu.agendasalas.dto.notificações.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.notificações.NotificacaoResponseDTO;

@CrossOrigin
@RestController
@RequestMapping("notificacoes")
@Tag(name = "Notificação", description = "Operações relacionadas a notificação")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    @Operation(summary = "Lista todas as notificações")
    public List<NotificacaoResponseDTO> listarNotificacoes() {
        return notificacaoService.listarNotificacoesComoDTO();
    }
    
    @PostMapping
    @Operation(summary = "Criar uma nova notificação")
    public void enviarNotificacoes(@RequestBody List<NotificacaoCreationDTO> notificacoesDTO) {
        notificacaoService.enviarNotificacoes(notificacoesDTO);
    }
}
