package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoDiasAgendadosDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.exceptions.SalaNaoEncontradaException;
import com.fatec.itu.agendasalas.exceptions.UsuarioNaoEncontradoException;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.EventoRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;


@Service
public class AgendamentoEventoService {
    
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private AgendamentoConflitoService agendamentoConflitoService;
    private JanelasHorarioRepository janelasHorarioRepository;
    private UsuarioRepository usuarioRepository;
    private SalaRepository salaRepository;
    private EventoRepository eventoRepository;
    private RecorrenciaRepository recorrenciaRepository;
    private AgendamentoAulaService agendamentoAulaService;

    public AgendamentoEventoService(
        AgendamentoEventoRepository agendamentoEventoRepository, 
        JanelasHorarioRepository janelasHorarioRepository,
        AgendamentoAulaService agendamentoAulaService, 
        UsuarioRepository usuarioRepository,
        SalaRepository salaRepository,
        EventoRepository eventoRepository,
        RecorrenciaRepository recorrenciaRepository,
        AgendamentoConflitoService agendamentoConflitoService){
            this.agendamentoEventoRepository = agendamentoEventoRepository;
            this.janelasHorarioRepository = janelasHorarioRepository;
            this.usuarioRepository = usuarioRepository;
            this.salaRepository = salaRepository;
            this.eventoRepository = eventoRepository;
            this.recorrenciaRepository = recorrenciaRepository;
            this.agendamentoConflitoService = agendamentoConflitoService;
            this.agendamentoAulaService = agendamentoAulaService;
        }

    @Transactional
    public void criarAgendamentoEvento(AgendamentoEventoCreationDTO agendamentoEventoCreationDTO) throws ConflitoAoAgendarException{ 
       
        LocalDate diaInicial = agendamentoEventoCreationDTO.diasAgendados().stream()
        .map(AgendamentoEventoDiasAgendadosDTO::dia)
        .min(LocalDate::compareTo)
        .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        
        LocalDate diaFinal = agendamentoEventoCreationDTO.diasAgendados().stream()
        .map(AgendamentoEventoDiasAgendadosDTO::dia)
        .max(LocalDate::compareTo)
        .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        

        Recorrencia recorrencia = new Recorrencia(diaInicial, diaFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);

        Usuario usuario = usuarioRepository.findById(agendamentoEventoCreationDTO.usuario())
            .orElseThrow(()-> new UsuarioNaoEncontradoException(agendamentoEventoCreationDTO.usuario()));
       
        Evento evento = eventoRepository.findByNome(agendamentoEventoCreationDTO.nomeEvento())
            .orElseThrow(()-> new RuntimeException("EVENTO NAO ENCONTRADO"));
        
        Sala sala = salaRepository.findById(agendamentoEventoCreationDTO.localId())
            .orElseThrow(()-> new SalaNaoEncontradaException(agendamentoEventoCreationDTO.localId()));

        for(AgendamentoEventoDiasAgendadosDTO agendamentoDia :  agendamentoEventoCreationDTO.diasAgendados()){

            for(Long janelaId : agendamentoDia.janelasHorarioId()){
                JanelasHorario janela = janelasHorarioRepository.findById(janelaId).orElseThrow(()-> new JanelasHorarioNaoEncontradaException(janelaId));
                AgendamentoAula agendamentoAulaConflitante = agendamentoConflitoService.filtrarAulasConflitantes(sala.getId(), agendamentoDia.dia(), janelaId);
                if(agendamentoAulaConflitante!=null){
                    //aqui preciso cancelar a aula, por enquanto vou deixar para excluir
                    //pra logica seria: definir o status como CANCELADO, criar um registro na tabela AGENDAMENTOS_CANCELADOS
                    agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaConflitante.getId());
                }
                if(agendamentoConflitoService.existeEventoNoHorario(sala.getId(), agendamentoDia.dia(), janelaId)){
                    throw new ConflitoAoAgendarException(sala.getNome(), agendamentoDia.dia(), janela.getHoraInicio(), janela.getHoraFim());
                }

                AgendamentoEvento agendamentoEvento = new AgendamentoEvento();
                agendamentoEvento.setUsuario(usuario);
                agendamentoEvento.setSala(sala);
                agendamentoEvento.setEventoId(evento);
                agendamentoEvento.setJanelasHorario(janela);
                agendamentoEvento.setData(agendamentoDia.dia());
                agendamentoEvento.setRecorrencia(recorrenciaSalva);
                agendamentoEvento.setTipoEvento(true);
                agendamentoEvento.setStatus("ATIVO");
                agendamentoEvento.setSolicitante(usuario.getNome());
                agendamentoEventoRepository.save(agendamentoEvento);

            }

        }
           
    }

    private AgendamentoEventoResponseDTO converterParaResponseDTO(AgendamentoEvento agendamentoEvento){
        return new AgendamentoEventoResponseDTO(
            agendamentoEvento.getId(),
            agendamentoEvento.getUsuario() != null ? agendamentoEvento.getUsuario().getNome() : null,
            agendamentoEvento.getSala() != null ? agendamentoEvento.getSala().getNome() : null,
            agendamentoEvento.getEventoId()!=null? agendamentoEvento.getEventoId().getNome(): null,
            agendamentoEvento.getData(),
            agendamentoEvento.getDiaDaSemana(),
            agendamentoEvento.getJanelasHorario().getHoraInicio(),
            agendamentoEvento.getJanelasHorario().getHoraFim(),
            agendamentoEvento.isTipoEvento(),
            agendamentoEvento.getRecorrencia().getId(),
            agendamentoEvento.getStatus()
        );
    }

    public List<AgendamentoEventoResponseDTO> listarAgendamentosEvento(){
         return agendamentoEventoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletarAgendamentoEventoEspecifico(Long agendamentoEventoId){
        agendamentoEventoRepository.deleteById(agendamentoEventoId);
    }

    public void deletarAgendamentoEventoPelaRecorrencia(Long recorrenciaId)
    {
        
    }  

    }

