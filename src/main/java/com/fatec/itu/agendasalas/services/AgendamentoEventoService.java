package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;


@Service
public class AgendamentoEventoService {
    
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private JanelasHorarioService janelasHorarioService;
    private UsuarioRepository usuarioRepository;
    private SalaRepository salaRepository;

    public AgendamentoEventoService(
        AgendamentoEventoRepository agendamentoEventoRepository, 
        JanelasHorarioService janelasHorarioService, 
        UsuarioRepository usuarioRepository,
        SalaRepository salaRepository,
        EventoRepository eventoRepository){
            this.agendamentoEventoRepository = agendamentoEventoRepository;
            this.janelasHorarioService = janelasHorarioService;
            this.usuarioRepository = usuarioRepository;
            this.salaRepository = salaRepository;
            this.eventoRepository = eventoRepository;
        }

    @Transactional
    public AgendamentoEventoResponseDTO criarAgendamentoEvento(AgendamentoEventoCreationDTO agendamentoEventoCreationDTO){ 
      
        
        List<JanelasHorario> janelasHorario = janelasHorarioService.buscaJanelaHorarioPelosHorariosInicioeFim(agendamentoEventoCreationDTO.horaInicio(), agendamentoEventoCreationDTO.horafim(), agendamentoEventoCreationDTO.todosHorarios());
        
        LocalDate dataInicial = agendamentoEventoCreationDTO.diaInicio(); 
        LocalDate dataFinal = agendamentoEventoCreationDTO.diaFim();
        Usuario usuario = usuarioRepository.getReferenceById(agendamentoEventoCreationDTO.usuario());
        Evento evento = eventoRepository.findByNome(agendamentoEventoCreationDTO.nomeEvento());
        Sala sala = salaRepository.findByNome(agendamentoEventoCreationDTO.local()).orElseThrow(()-> new RuntimeException("SALA NAO ENCONTRADA"));

        while(!dataInicial.isAfter(dataFinal)){

            for(JanelasHorario janela : janelasHorario){
                AgendamentoEvento agendamentoEvento = new AgendamentoEvento();
                agendamentoEvento.setUsuario(usuario);
                agendamentoEvento.setSala(sala);
                agendamentoEvento.setEventoId(evento);
                agendamentoEvento.setJanelasHorario(janela);
                agendamentoEvento.setTipo(dto.tipoAgendamento());

            }
            dataInicial = dataFinal.plusDays(1);
            
            }
    }

    }

/*Falta fazer:
 * O id do usuario vem do JWT, arrumar isso.
 * Para cada dia -> faz agendamentos para cada janela de horario
 * Avança 1 dia
 * Mudar o BD, usar
 */