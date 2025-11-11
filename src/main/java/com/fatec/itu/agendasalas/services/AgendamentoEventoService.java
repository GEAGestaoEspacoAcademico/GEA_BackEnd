package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;


@Service
public class AgendamentoEventoService {
    
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private JanelasHorarioService janelasHorarioService;

    public AgendamentoEventoService(AgendamentoEventoRepository agendamentoEventoRepository, JanelasHorarioService janelasHorarioService){
        this.agendamentoEventoRepository = agendamentoEventoRepository;
        this.janelasHorarioService = janelasHorarioService;
    }



   
    @Transactional
    public AgendamentoEventoResponseDTO criarAgendamentoEvento(AgendamentoEventoCreationDTO agendamentoEventoCreationDTO){ 
        List<JanelasHorario> janelasHorario;
        
        janelasHorario = janelasHorarioService.buscaJanelaHorarioPelosHorariosInicioeFim(agendamentoEventoCreationDTO.horaInicio(), agendamentoEventoCreationDTO.horafim(), agendamentoEventoCreationDTO.todosHorarios());
        
        
        LocalDate dataInicial = agendamentoEventoCreationDTO.diaInicio(); 
        LocalDate dataFinal = agendamentoEventoCreationDTO.diaFim();

    }
}
