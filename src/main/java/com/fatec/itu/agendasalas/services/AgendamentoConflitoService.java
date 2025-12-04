package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.AgendamentoAula;
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

    
    public boolean existeEventoNoHorario(Long sala, LocalDate data, Long janela){
        return agendamentoEventoRepository.existsBySalaIdAndDataAndJanelasHorarioId(sala, data, janela);
    }

    public boolean existeAgendamentoNoHorario(Long sala, LocalDate data, Long janela){
        return agendamentoRepository.existsBySalaIdAndDataAndJanelasHorarioId(sala, data, janela);
    }

    public AgendamentoAula filtrarAulasConflitantes(Long sala, LocalDate data, Long janela){
        return agendamentoAulaRepository.findBySalaIdAndDataAndJanelasHorarioId(sala, data, janela);
    }
    

    public boolean dataNoPassado(LocalDate data) {
        return data.isBefore(LocalDate.now());
    }    

    public boolean janelaHorarioPassou(LocalDate data, LocalTime inicio, LocalTime fim){
        if(!data.isEqual(LocalDate.now())){
            return false;
        }

        return fim.isBefore(LocalTime.now());
    }


}
