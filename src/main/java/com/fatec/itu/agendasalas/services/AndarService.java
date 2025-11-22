package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.andaresDTO.AndarResponseDTO;
import com.fatec.itu.agendasalas.entity.Andar;
import com.fatec.itu.agendasalas.repositories.AndarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AndarService {

    private final AndarRepository andarRepository;

    public Andar buscarPorId(Long id) {
        return andarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Andar não encontrado"));
    }

    public List<AndarResponseDTO> listar() {
        return andarRepository.findAll()
                .stream()
                .map(a -> new AndarResponseDTO(a.getId(), a.getNome()))
                .toList();
    }
}


