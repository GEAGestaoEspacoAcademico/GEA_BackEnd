package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.EventoRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;


@Service
public class AgendamentoEventoService {
    
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private AgendamentoConflitoService agendamentoConflitoService;
    private JanelasHorarioService janelasHorarioService;
    private UsuarioRepository usuarioRepository;
    private SalaRepository salaRepository;
    private EventoRepository eventoRepository;
    private RecorrenciaRepository recorrenciaRepository;

    public AgendamentoEventoService(
        AgendamentoEventoRepository agendamentoEventoRepository, 
        JanelasHorarioService janelasHorarioService, 
        UsuarioRepository usuarioRepository,
        SalaRepository salaRepository,
        EventoRepository eventoRepository,
        RecorrenciaRepository recorrenciaRepository,
        AgendamentoConflitoService agendamentoConflitoService){
            this.agendamentoEventoRepository = agendamentoEventoRepository;
            this.janelasHorarioService = janelasHorarioService;
            this.usuarioRepository = usuarioRepository;
            this.salaRepository = salaRepository;
            this.eventoRepository = eventoRepository;
            this.recorrenciaRepository = recorrenciaRepository;
            this.agendamentoConflitoService = agendamentoConflitoService;
        }

    @Transactional
    public void criarAgendamentoEvento(AgendamentoEventoCreationDTO agendamentoEventoCreationDTO){ 
      
        
        List<JanelasHorario> janelasHorario = janelasHorarioService.buscaJanelaHorarioPelosHorariosInicioeFim(agendamentoEventoCreationDTO.horaInicio(), agendamentoEventoCreationDTO.horafim(), agendamentoEventoCreationDTO.todosHorarios());
        
        LocalDate dataInicial = agendamentoEventoCreationDTO.diaInicio(); 
        LocalDate dataFinal = agendamentoEventoCreationDTO.diaFim();
        Recorrencia recorrencia = new Recorrencia(dataInicial, dataFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);


        Usuario usuario = usuarioRepository.getReferenceById(agendamentoEventoCreationDTO.usuario());
        Evento evento = eventoRepository.findByNome(agendamentoEventoCreationDTO.nomeEvento()).orElseThrow(()-> new RuntimeException("EVENTO NAO ENCONTRADA"));;
        Sala sala = salaRepository.findByNome(agendamentoEventoCreationDTO.local()).orElseThrow(()-> new RuntimeException("SALA NAO ENCONTRADA"));


        while(!dataInicial.isAfter(dataFinal)){

            for(JanelasHorario janela : janelasHorario){
                AgendamentoAula agendamentoAulaConflitante = agendamentoConflitoService.filtrarAulasConflitantes(sala, dataInicial, janela);
                if(agendamentoConflitoService.existeEventoNoHorario(sala, dataInicial, janela)){
                    throw new ConflitoAoAgendarException();
                }
                AgendamentoEvento agendamentoEvento = new AgendamentoEvento();
                agendamentoEvento.setUsuario(usuario);
                agendamentoEvento.setSala(sala);
                agendamentoEvento.setEventoId(evento);
                agendamentoEvento.setJanelasHorario(janela);
                agendamentoEvento.setData(dataInicial);
                agendamentoEvento.setRecorrencia(recorrenciaSalva);
                agendamentoEvento.setTipo("EVENTO");
                agendamentoEventoRepository.save(agendamentoEvento);

            }
            dataInicial = dataInicial.plusDays(1);
            
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
            agendamentoEvento.getTipo(),
            agendamentoEvento.getRecorrencia().getId()
        );
    }

    public List<AgendamentoEventoResponseDTO> listarAgendamentosEvento(){
         return agendamentoEventoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    }

