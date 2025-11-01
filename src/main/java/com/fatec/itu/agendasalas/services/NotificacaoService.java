package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.NotificacaoResponseDTO;
import com.fatec.itu.agendasalas.dto.SalaResumoDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResumoDTO;
import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.entity.Notificacao;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.NotificacaoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listarNotificacoesComoDTO() {
        return notificacaoRepository.findAll().stream()
               .map(this::converterParaResponseDTO)
               .collect(Collectors.toList());
    }

    public void enviarNotificacoes(List<NotificacaoCreationDTO> notificacoesDTO) {
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
            notificacao.setDataEnvio(LocalDate.now());
            notificacao.setUsuarioRemetente(remetente);
            notificacao.setDestinatario(destinatarios);

            notificacaoRepository.save(notificacao);
        }
    }

    private NotificacaoResponseDTO converterParaResponseDTO(Notificacao notificacao) {

        UsuarioResumoDTO remetenteDTO = new UsuarioResumoDTO(
            notificacao.getUsuarioRemetente().getId(), 
            notificacao.getUsuarioRemetente().getNome()
        );

        List<UsuarioResumoDTO> destinatariosDTO = notificacao.getDestinatario().stream()
                .map(dest -> new UsuarioResumoDTO(dest.getId(), dest.getNome()))
                .collect(Collectors.toList());

        AgendamentoNotificacaoDTO agendamentoDTO = converterAgendamentoParaNotificacaoDTO(notificacao.getAgendamento());

        return new NotificacaoResponseDTO(
            notificacao.getIdNotificacao(),
            notificacao.getTitulo(),
            notificacao.getMensagem(),
            notificacao.getDataEnvio(),
            remetenteDTO,
            destinatariosDTO,
            agendamentoDTO
        );
    }

    private AgendamentoNotificacaoDTO converterAgendamentoParaNotificacaoDTO(Agendamento agendamento) {

        Sala sala = agendamento.getSala();
        SalaResumoDTO salaDTO = new SalaResumoDTO(sala.getId(), sala.getNome());
        LocalTime horaInicio = null;
        LocalTime horaFim = null;
        if (agendamento.getJanelasHorario() != null) {
            horaInicio = agendamento.getJanelasHorario().getHoraInicio();
            horaFim = agendamento.getJanelasHorario().getHoraFim();
        }

        return new AgendamentoNotificacaoDTO(
            agendamento.getId(),
            salaDTO,
            agendamento.getDataInicio(),
            agendamento.getDataFim(),
            horaInicio,
            horaFim
        );
    }
    
    public Notificacao enviarNotificacao(Notificacao notificacao) {

        if (notificacao.getAgendamento() == null || notificacao.getAgendamento().getId() == null) {
            throw new IllegalArgumentException("Agendamento inválido ou não informado");
        }

        Agendamento agendamento = agendamentoRepository.findById(notificacao.getAgendamento().getId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        notificacao.setAgendamento(agendamento);

        if (notificacao.getDestinatario() == null || notificacao.getDestinatario().isEmpty()) {
            throw new IllegalArgumentException("A lista de destinatários não pode estar vazia");
        }

        if (notificacao.getDataEnvio() == null) {
            notificacao.setDataEnvio(LocalDate.now());
        }

        return notificacaoRepository.save(notificacao);
    }
}
