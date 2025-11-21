package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.entity.Secretaria;
import com.fatec.itu.agendasalas.exceptions.SecretariaNaoEncontradoException;
import com.fatec.itu.agendasalas.repositories.SecretariaRepository;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    public List<SecretariaResponseDTO> listarSecretarios() {
        return secretariaRepository.findAll().stream()
        .map(this::converterParaResponseDTO)
        .toList();
    }

    public SecretariaResponseDTO buscarSecretarioPorId(Long id){
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new SecretariaNaoEncontradoException(id));
        return converterParaResponseDTO(secretaria);
    }

    private SecretariaResponseDTO converterParaResponseDTO(Secretaria secretaria){
        return new SecretariaResponseDTO(secretaria.getId(), secretaria.getNome(), secretaria.getEmail(), secretaria.getMatricula(), secretaria.getCargo().getId());
    }


    
}
