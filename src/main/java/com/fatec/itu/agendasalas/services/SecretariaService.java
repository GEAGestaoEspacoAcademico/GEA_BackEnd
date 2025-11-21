package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.repositories.SecretariaRepository;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    public List<SecretariaResponseDTO> listarSecretarios() {
        return secretariaRepository.findAll();
    }

    private SecretariaResponseDTO converterParaResponseDTO(Secretaria secretaria){

    }
    
}
