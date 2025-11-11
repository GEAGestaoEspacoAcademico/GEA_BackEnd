package com.fatec.itu.agendasalas.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioCreationDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioUpdateDTO;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;


@Service
public class JanelasHorarioService {

    @Autowired
    private JanelasHorarioRepository janelasHorarioRepository;

    public List<JanelasHorarioResponseDTO> listarTodasJanelasHorario(){

        List<JanelasHorario> listaJanelasHorario = janelasHorarioRepository.findAll();
        List<JanelasHorarioResponseDTO> listaJanelasHorarioResponseDTOs = new ArrayList<JanelasHorarioResponseDTO>();
        for(JanelasHorario janela : listaJanelasHorario){
            listaJanelasHorarioResponseDTOs.add(transformarEmJanelasHorarioResponseDTO(janela));
        }
        
        return listaJanelasHorarioResponseDTOs;
    }

    private JanelasHorarioResponseDTO transformarEmJanelasHorarioResponseDTO(JanelasHorario janelaHorario){
        return new JanelasHorarioResponseDTO(janelaHorario.getId(), janelaHorario.getHoraInicio(), janelaHorario.getHoraFim());
    }

    @Transactional
    public JanelasHorarioResponseDTO criarJanelaHorario(JanelasHorarioCreationDTO janelasHorarioCreationDTO) {
        JanelasHorario janelasHorarioCreation = new JanelasHorario(janelasHorarioCreationDTO.horaInicio(), janelasHorarioCreationDTO.horaFim());
        JanelasHorario janelasHorarioSalvo = janelasHorarioRepository.save(janelasHorarioCreation);

        return transformarEmJanelasHorarioResponseDTO(janelasHorarioSalvo);
    }

    public JanelasHorarioResponseDTO filtrarJanelaHorarioPeloID(Long id) {
        JanelasHorario janelasHorario = janelasHorarioRepository.findById(id).orElseThrow(()->new RuntimeException("Janela de Horário com esse id não foi encontrada"));
        return transformarEmJanelasHorarioResponseDTO(janelasHorario);
    }

    @Transactional
    public JanelasHorarioResponseDTO atualizarJanelasHorario(Long id, JanelasHorarioUpdateDTO janelasHorarioUpdateDTO) {
       JanelasHorario janelasHorarioAntiga = janelasHorarioRepository.findById(id).orElseThrow(()->new RuntimeException("Janela de Horário com esse id não foi encontrada"));
       janelasHorarioAntiga.setHoraInicio(janelasHorarioUpdateDTO.horaInicio());
       janelasHorarioAntiga.setHoraFim(janelasHorarioUpdateDTO.horaFim());
       JanelasHorario janelasHorarioAtualizada = janelasHorarioRepository.save(janelasHorarioAntiga);
       return transformarEmJanelasHorarioResponseDTO(janelasHorarioAtualizada);
    }

    public List<JanelasHorario> buscaJanelaHorarioPelosHorariosInicioeFim(LocalTime horaInicio, LocalTime horaFim, boolean todosHorarios){
        List<JanelasHorario> listaJanelas = new ArrayList<JanelasHorario>();
        if(!todosHorarios){
            listaJanelas = janelasHorarioRepository.findAll();
        }
        else{
            listaJanelas.add(janelasHorarioRepository.findByHoraInicioAndHoraFim(horaInicio, horaFim));
        }

        return listaJanelas;
    }

}
