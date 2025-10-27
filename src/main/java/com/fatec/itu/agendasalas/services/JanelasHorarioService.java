package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fatec.itu.agendasalas.dto.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;

@Service
public class JanelasHorarioService {

    @Autowired
    private JanelasHorarioRepository janelasHorarioRepository;

    public List<JanelasHorarioResponseDTO> listarTodasJanelasHorario(){
        return janelasHorarioRepository.findAll()
        .stream()
        .map(this::transformarEmJanelasHorarioResponseDTO)
        .toList();
    }

    private JanelasHorarioResponseDTO transformarEmJanelasHorarioResponseDTO(JanelasHorario janelaHorario){
        return new JanelasHorarioResponseDTO(janelaHorario.getId(), janelaHorario.getHoraInicio(), janelaHorario.getHoraFim());
    }
}
