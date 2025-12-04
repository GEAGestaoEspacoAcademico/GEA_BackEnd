package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDTO;
import com.fatec.itu.agendasalas.dto.notificações.NotificacaoCreationDTO;
import com.fatec.itu.agendasalas.dto.notificações.NotificacaoResponseDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaResumoDTO;
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

    @Transactional
        public long enviarNotificacao(NotificacaoCreationDTO dto) {
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
            notificacao.setTitulo(dto.notificacaoTitulo());
            notificacao.setMensagem(dto.notificacaoMensagem());
            notificacao.setDataEnvio(LocalDate.now());
            notificacao.setUsuarioRemetente(remetente);
            notificacao.setDestinatario(destinatarios);

            Notificacao salvo = notificacaoRepository.save(notificacao);
            return salvo.getIdNotificacao();
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
            agendamento.getData(),
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
