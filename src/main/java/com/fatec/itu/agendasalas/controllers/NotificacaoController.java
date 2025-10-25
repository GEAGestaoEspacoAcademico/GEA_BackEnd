package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.entity.Notificacao;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.NotificacaoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;
import com.fatec.itu.agendasalas.services.NotificacaoService;
import com.fatec.itu.agendasalas.dto.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.NotificacaoResponseDTO;

import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("listar")
    public List<NotificacaoResponseDTO> listarNotificacoes() {
        return notificacaoService.listarNotificacoesComoDTO();
    }
    
    @PostMapping("/enviar")
    public void enviarNotificacoes(@RequestBody List<NotificacaoCreationDTO> notificacoesDTO) {
        for (NotificacaoCreationDTO dto : notificacoesDTO) {

            if (dto.destinatarios() == null || dto.destinatarios().isEmpty()) {
                throw new IllegalArgumentException("A lista de destinatários não pode ser nula ou vazia.");
            }

            Agendamento agendamento = agendamentoRepository.findById(dto.agendamento())
                    .orElseThrow(() -> new IllegalArgumentException("Agendamento inválido ou não informado"));

            Usuario remetente = usuarioRepository.findById(dto.usuarioRemetente())
                    .orElseThrow(() -> new IllegalArgumentException("Remetente inválido"));

            List<Usuario> destinatarios = usuarioRepository.findAllById(dto.destinatarios());

            Notificacao notificacao = new Notificacao();
            notificacao.setAgendamento(agendamento);
            notificacao.setTitulo(dto.titulo());
            notificacao.setMensagem(dto.mensagem());
            notificacao.setDataEnvio(dto.dataEnvio());
            notificacao.setUsuarioRemetente(remetente);
            notificacao.setDestinatario(destinatarios);
            notificacao.setCanalEnvio(dto.canalEnvio());

            notificacaoRepository.save(notificacao);
        }
    }
}
