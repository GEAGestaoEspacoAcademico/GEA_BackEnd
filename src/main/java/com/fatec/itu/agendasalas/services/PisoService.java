package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.andaresDTO.PisoResponseDTO;
import com.fatec.itu.agendasalas.entity.Piso;
import com.fatec.itu.agendasalas.repositories.PisoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PisoService {

    private final PisoRepository pisoRepository;

    public Piso buscarPorId(Long id) {
        return pisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piso não encontrado"));
    }

    public List<PisoResponseDTO> listar() {
        return pisoRepository.findAll()
                .stream()
                .map(a -> new PisoResponseDTO(a.getId(), a.getNome()))
                .toList();
    }
}
