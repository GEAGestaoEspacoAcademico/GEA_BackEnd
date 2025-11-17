package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoDTO;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDisciplinaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaResumoDTO;
import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<AgendamentoDTO> listarAgendamentos() {

        List<Agendamento> listaAgendamentos = agendamentoRepository.findAll();
        List<AgendamentoDTO> listaAgendamentoDTOS = new ArrayList<>();
        for (Agendamento agendamento : listaAgendamentos) {
            AgendamentoDTO agendamentoDTO = conversaoAgendamentoParaDTO(agendamento);
            listaAgendamentoDTOS.add(agendamentoDTO);
        }
        return listaAgendamentoDTOS;
    }

    private AgendamentoDTO conversaoAgendamentoParaDTO(Agendamento agendamento){
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO(
        agendamento.getUsuario().getNome(), 
        agendamento.getSala().getNome(),
        agendamento.getData(), 
        agendamento.getDiaDaSemana(), 
        agendamento.getJanelasHorario().getHoraInicio(), 
        agendamento.getJanelasHorario().getHoraFim(),
        agendamento.getIsEvento()
        );

        return agendamentoDTO;
    }

    public List<AgendamentoNotificacaoDisciplinaDTO> listarAgendamentosDisciplina() {
        List<Agendamento> listaAgendamentos = agendamentoRepository.findAll();

        return listaAgendamentos.stream()
            .map(this::conversaoAgendamentoParaNotificacaoDisciplinaDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendamentoNotificacaoDisciplinaDTO> buscarAgendamentosPorData(LocalDate data) {
        
        List<Agendamento> agendamentos = agendamentoRepository.findAllByData(data);

        return agendamentos.stream()
                .map(this::conversaoAgendamentoParaNotificacaoDisciplinaDTO)
                .collect(Collectors.toList());
    }

    private AgendamentoNotificacaoDisciplinaDTO conversaoAgendamentoParaNotificacaoDisciplinaDTO(Agendamento agendamento) {
        SalaResumoDTO salaResumo = null;
        if (agendamento.getSala() != null) {
            salaResumo = new SalaResumoDTO(
                agendamento.getSala().getId(),
                agendamento.getSala().getNome()
        );
        }

        Long disciplinaId = null;
        String disciplinaNome = null;
        boolean isEvento = agendamento.getIsEvento();

        if (!isEvento && agendamento instanceof AgendamentoAula aula) {
            if (aula.getDisciplina() != null) {
                disciplinaId = aula.getDisciplina().getId();
                disciplinaNome = aula.getDisciplina().getNome();
            }
        }

        return new AgendamentoNotificacaoDisciplinaDTO(
            agendamento.getId(),
            salaResumo,
            agendamento.getData(),
            agendamento.getJanelasHorario() != null ? agendamento.getJanelasHorario().getHoraInicio() : null,
            agendamento.getJanelasHorario() != null ? agendamento.getJanelasHorario().getHoraFim() : null,
            disciplinaId,
            disciplinaNome,
            isEvento);
    }
}
