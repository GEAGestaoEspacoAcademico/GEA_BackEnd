package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.eventosDTO.EventoCreationDTO;
import com.fatec.itu.agendasalas.dto.eventosDTO.EventoResponseDTO;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.repositories.EventoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;


    @Transactional
    public EventoResponseDTO criar(EventoCreationDTO dto) {
        validarCamposObrigatorios(dto);

        eventoRepository.findByNome(dto.nomeEvento()).ifPresent(e -> {
            throw new DataIntegrityViolationException("Já existe um evento com esse nome.");
        });

        Evento evento = new Evento();
        evento.setNome(dto.nomeEvento());
        evento.setDescricao(dto.descricaoEvento());

        Evento salvo = eventoRepository.save(evento);

        return converterParaResponse(salvo);
    }


    public List<EventoResponseDTO> listar() {
        return eventoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

 
    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento com id: " + id + " não encontrado."));

        return converterParaResponse(evento);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoCreationDTO dto) {
        validarCamposObrigatorios(dto);

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento com id: " + id + " não encontrado."));

        eventoRepository.findByNome(dto.nomeEvento()).ifPresent(e -> {
            if (!e.getId().equals(id)) {
                throw new DataIntegrityViolationException("Já existe outro evento com esse nome.");
            }
        });

        evento.setNome(dto.nomeEvento());
        evento.setDescricao(dto.descricaoEvento());

        Evento atualizado = eventoRepository.save(evento);

        return converterParaResponse(atualizado);
    }


    @Transactional
    public void excluir(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento com id: " + id + " não encontrado."));

        try {
            eventoRepository.delete(evento);
            eventoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir o evento pois ele está vinculado a um ou mais agendamentos.");
        }
    }

    private void validarCamposObrigatorios(EventoCreationDTO dto) {
        if (dto.nomeEvento() == null || dto.nomeEvento().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do evento é obrigatório.");
        }
    }

    private EventoResponseDTO converterParaResponse(Evento evento) {
        return new EventoResponseDTO(
                evento.getNome(),
                evento.getDescricao()
        );
    }
}
