package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoCanceladoRequestDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoCanceladoResponseDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoNotificacaoDisciplinaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaResumoDTO;
import com.fatec.itu.agendasalas.entity.Agendamento;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoCancelado;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoCanceladoRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private AgendamentoAulaRepository agendamentoAulaRepository;

    @Autowired
    private AgendamentoEventoRepository agendamentoEventoRepository;

    @Autowired
    private AgendamentoCanceladoRepository agendamentoCanceladoRepository;
    
    @Autowired
    private com.fatec.itu.agendasalas.repositories.UsuarioRepository usuarioRepository;

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

    @Transactional
    public AgendamentoCanceladoResponseDTO cancelarAgendamento(
        Long agendamentoId,
        AgendamentoCanceladoRequestDTO request) {
        
        Usuario usuarioCancelador = usuarioRepository.findById(request.usuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Agendamento agendamentoOriginal = agendamentoRepository.findById(agendamentoId)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + agendamentoId));

        String motivoCancelamento = request.motivoCancelamento();

        AgendamentoCancelado registroCancelado = buildRegistroCancelado(
            agendamentoOriginal,
            usuarioCancelador,
            motivoCancelamento
        );

        AgendamentoCancelado registroPersistido = agendamentoCanceladoRepository.save(registroCancelado);

        agendamentoRepository.deleteById(agendamentoId);

        return AgendamentoCanceladoResponseDTO.builder()
            .idCancelamento(registroPersistido.getId())
            .agendamentoOriginalId(registroPersistido.getAgendamentoOriginalId())
            .tipoAgendamento(registroPersistido.getTipoAgendamento())
            .motivoCancelamento(registroPersistido.getMotivoCancelamento())
            .dataHoraCancelamento(registroPersistido.getDataHoraCancelamento())
            .build();
    }

    private AgendamentoCancelado buildRegistroCancelado(
        Agendamento agendamentoOriginal, 
        Usuario usuarioCancelador,
        String motivoCancelamento
    ) {
    
        AgendamentoCancelado.AgendamentoCanceladoBuilder builder = AgendamentoCancelado.builder()
            .agendamentoOriginalId(agendamentoOriginal.getId())
            .data(agendamentoOriginal.getData())
            .statusOriginal(agendamentoOriginal.getStatus())
            .solicitante(agendamentoOriginal.getSolicitante())
            .motivoCancelamento(motivoCancelamento)
            .dataHoraCancelamento(LocalDateTime.now())
            .usuario(agendamentoOriginal.getUsuario()) 
            .canceladoPor(usuarioCancelador) 
            .sala(agendamentoOriginal.getSala())
            .janelasHorario(agendamentoOriginal.getJanelasHorario()) 
            .disciplina(null) 
            .evento(null);    

        if (agendamentoOriginal instanceof AgendamentoAula aula) {
            builder.disciplina(aula.getDisciplina()); 
            builder.tipoAgendamento("AULA");
        
        } else if (agendamentoOriginal instanceof AgendamentoEvento evento) {
            builder.evento(evento.getEvento()); 
            builder.tipoAgendamento("EVENTO");
    }

        return builder.build();
    }
    
    

}
