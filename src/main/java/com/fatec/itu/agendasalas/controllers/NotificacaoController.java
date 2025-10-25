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
import com.fatec.itu.agendasalas.dto.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.NotificacaoResponseDTO;

@CrossOrigin
@RestController
@RequestMapping("notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("listar")
    public List<NotificacaoResponseDTO> listarNotificacoes() {
        return notificacaoService.listarNotificacoesComoDTO();
    }
    
    @PostMapping("/enviar")
    public void enviarNotificacoes(@RequestBody List<NotificacaoCreationDTO> notificacoesDTO) {
        notificacaoService.enviarNotificacoes(notificacoesDTO);
    }
}
