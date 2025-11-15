package com.fatec.itu.agendasalas.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoDTO;
import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<AgendamentoDTO> listarAgendamentos() {
        
        List<Agendamento> listaAgendamentos = agendamentoRepository.findAll();
        List<AgendamentoDTO> listaAgendamentoDTOS = new ArrayList<>();
        for(Agendamento agendamento : listaAgendamentos){
            AgendamentoDTO agendamentoDTO = conversaoAgendamentoParaDTO(agendamento);
            listaAgendamentoDTOS.add(agendamentoDTO);
        }
        return listaAgendamentoDTOS;
    }

    private AgendamentoDTO conversaoAgendamentoParaDTO(Agendamento agendamento){
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO(
        agendamento.getUsuario().getNome(), 
        agendamento.getData(),
        agendamento.getDiaDaSemana(), 
        agendamento.getJanelasHorario().getHoraInicio(), 
        agendamento.getJanelasHorario().getHoraFim(), 
        agendamento.isTipoEvento()
        );
        
        return agendamentoDTO;

    }
   
    
    


}
