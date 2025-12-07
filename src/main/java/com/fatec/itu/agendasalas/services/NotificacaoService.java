package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Notificacao;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.ListaDeAulasVaziaNotificacaoException;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.NotificacaoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

import jakarta.mail.MessagingException;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private AgendamentoAulaRepository agendamentoAulaRepository;
    
    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public void notificarAoCriarAgendamentoAulaRecorrente(Recorrencia recorrenciaAulas) throws MessagingException{
        //var auth = SecurityContextHolder.getContext().getAuthentication();
        
        //if(auth != null && auth.getPrincipal() instanceof Usuario){
        //      remetente = (Usuario) auth.getPrincipal();
         //  }

        //NÃO APAGAR ESSAS LINHAS COMENTADAS, ELA SERÁ UTIL COM A JWT.

        List<AgendamentoAula> aulas = agendamentoAulaRepository.findByRecorrenciaId(recorrenciaAulas.getId());
        if(aulas.isEmpty()){
            throw new ListaDeAulasVaziaNotificacaoException();
        }
        //apenas para teste, deve ser: Usuario destinatario = aulas.get(0).getUsuario();
        Usuario destinatario = aulas.get(0).getUsuario();
        
        Disciplina disciplina = aulas.get(0).getDisciplina();
        String diaDaSemana = aulas.get(0).getDiaDaSemana();
        //Usuario remetente = null; isso deverá estar na primeira linha
        Usuario remetente = destinatario;

        LocalDate diaInicial = aulas.stream()
            .map(AgendamentoAula::getData)
            .min(Comparator.naturalOrder())
            .orElseThrow(() -> new RuntimeException());

        LocalDate diaFinal = aulas.stream()
            .map(AgendamentoAula::getData)
            .max(Comparator.naturalOrder())
            .orElseThrow(() -> new RuntimeException());

        Set<JanelasHorario> janelasHorarios = aulas.stream()
            .map(AgendamentoAula::getJanelasHorario)
            .sorted(Comparator.comparing(JanelasHorario::getHoraInicio))
            .collect(Collectors.toCollection(LinkedHashSet::new));

        String nomeProfessor = aulas.get(0).getDisciplina().getProfessor().getNome();
        String dataFormatada = diaInicial.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + diaFinal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Notificacao notificacao = Notificacao.builder()
             .agendamentoId(aulas.get(0).getId())
             .data(diaInicial)
             .horaInicio(aulas.get(0).getJanelasHorario().getHoraInicio())
             .horaFim(aulas.get(0).getJanelasHorario().getHoraFim())
             .titulo("AGENDAMENTO REALIZADO")
             .mensagem("AGENDAMENTO REALIZADO NOS DIAS: " + dataFormatada + " PARA O PROFESSOR: " + nomeProfessor + " PARA A DISCIPLINA DE: " + disciplina.getNome())
             .dataEnvio(LocalDate.now())
             .usuarioRemetente(remetente)
             .destinatario(destinatario)
             .build();
        
        notificacaoRepository.save(notificacao);
        emailSenderService.enviarNotificacaoAgendamento(diaInicial, diaFinal, janelasHorarios, disciplina, remetente, destinatario, diaDaSemana);
    }

    
    public void notificarCadastro(Usuario usuario, String primeiraSenha) throws MessagingException{
        emailSenderService.enviarNotificacaoCadastro(usuario, primeiraSenha);
    }

    public void notificarCancelamentoDeAula(AgendamentoAula aula, String motivo, Usuario usuarioCancelador) throws MessagingException{
        String diaCancelado = aula.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Disciplina disciplina = aula.getDisciplina();
        String mensagem =  "AGENDAMENTO DO DIA: " + diaCancelado + " FOI CANCELADO PELO USUÁRIO: "  + usuarioCancelador.getNome()  + " MOTIVO: " + motivo + " DISCIPLINA: " + disciplina.getNome();
        Notificacao notificacao = Notificacao.builder()
        .agendamentoId(aula.getId())
        .data(aula.getData())
        .horaInicio(aula.getJanelasHorario().getHoraInicio())
        .horaFim(aula.getJanelasHorario().getHoraFim())
        .titulo("AGENDAMENTO CANCELADO")
        .mensagem(mensagem)
        .dataEnvio(LocalDate.now())
        .usuarioRemetente(usuarioCancelador)
        .destinatario(disciplina.getProfessor())
        .build();
        notificacaoRepository.save(notificacao);

        emailSenderService.enviarNotificacaoCancelamentoAula(disciplina, aula, motivo, usuarioCancelador);
    }


/* 
    @Deprecated
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
            notificacao.setAgendamentoId(agendamento.getId());
            notificacao.setTitulo(dto.notificacaoTitulo());
            notificacao.setMensagem(dto.notificacaoMensagem());
            notificacao.setDataEnvio(LocalDate.now());
            notificacao.setUsuarioRemetente(remetente);
            notificacao.setDestinatario(destinatarios.get(0));

            notificacaoRepository.save(notificacao);
        }
    }

    @Deprecated
    private NotificacaoResponseDTO converterParaResponseDTO(Notificacao notificacao) {

        UsuarioResumoDTO remetenteDTO = new UsuarioResumoDTO(
            notificacao.getUsuarioRemetente().getId(), 
            notificacao.getUsuarioRemetente().getNome()
        );

       UsuarioResumoDTO destinatarioDTO = new UsuarioResumoDTO(notificacao.getDestinatario().getId(), notificacao.getDestinatario().getNome());
            

        AgendamentoNotificacaoDTO agendamentoDTO = converterAgendamentoParaNotificacaoDTO(notificacao.getAgendamentoId());

        return new NotificacaoResponseDTO(
            notificacao.getIdNotificacao(),
            notificacao.getTitulo(),
            notificacao.getMensagem(),
            notificacao.getDataEnvio(),
            remetenteDTO,
            destinatarioDTO,
            agendamentoDTO
        );
        
    }

    @Deprecated
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

    @Deprecated
    public Notificacao enviarNotificacao(Notificacao notificacao) {

        if (notificacao.getAgendamentoId() == null) {
            throw new IllegalArgumentException("Agendamento inválido ou não informado");
        }

        Agendamento agendamento = agendamentoRepository.findById(notificacao.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        notificacao.setAgendamentoId(agendamento.getId());

        if (notificacao.getDestinatario() == null) {
            throw new IllegalArgumentException("O destinatario nao pode ser nulo");
        }

        if (notificacao.getDataEnvio() == null) {
            notificacao.setDataEnvio(LocalDate.now());
        }

        return notificacaoRepository.save(notificacao);
    }
    */
}
