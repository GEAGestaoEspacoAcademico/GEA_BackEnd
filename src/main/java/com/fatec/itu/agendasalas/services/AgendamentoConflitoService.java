package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;

@Service
public class AgendamentoConflitoService {

    private AgendamentoRepository agendamentoRepository;
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private AgendamentoAulaRepository agendamentoAulaRepository;

    public AgendamentoConflitoService(
        AgendamentoRepository agendamentoRepository,
        AgendamentoEventoRepository agendamentoEventoRepository,
        AgendamentoAulaRepository agendamentoAulaRepository){
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoEventoRepository = agendamentoEventoRepository;
        this.agendamentoAulaRepository = agendamentoAulaRepository;
    }

    
    public boolean existeEventoNoHorario(Sala sala, LocalDate data, JanelasHorario janela){
        return agendamentoEventoRepository.existsBySalaIdAndDataAndJanelasHorarioId(sala.getId(), data, janela.getId());
    }

    public boolean existeAgendamentoNoHorario(Sala sala, LocalDate data, JanelasHorario janela){
        return agendamentoRepository.existsBySalaIdAndDataAndJanelasHorarioId(sala.getId(), data, janela.getId());
    }

    public AgendamentoAula filtrarAulasConflitantes(Sala sala, LocalDate data, JanelasHorario janela){
        return agendamentoAulaRepository.findBySalaIdAndDataAndJanelasHorarioId(sala.getId(), data, janela.getId());
    }


}
