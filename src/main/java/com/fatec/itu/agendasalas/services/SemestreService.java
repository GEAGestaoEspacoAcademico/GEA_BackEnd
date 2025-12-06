package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.semestreDTO.SemestreListDTO;
import com.fatec.itu.agendasalas.repositories.SemestreRepository;

@Service
public class SemestreService {

    private final SemestreRepository semestreRepository;

    public SemestreService(SemestreRepository semestreRepository) {
        this.semestreRepository = semestreRepository;
    }

    public List<SemestreListDTO> listarTodos() {
        return semestreRepository.findAll().stream()
                .map(s -> new SemestreListDTO(s.getId(), s.getNome()))
                .collect(Collectors.toList());
    }
}
