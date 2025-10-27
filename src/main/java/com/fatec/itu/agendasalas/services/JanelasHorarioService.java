package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.JanelasHorarioCreationDTO;
import com.fatec.itu.agendasalas.dto.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.dto.JanelasHorarioUpdateDTO;
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

    public JanelasHorarioResponseDTO atualizarJanelasHorario(Long id, JanelasHorarioUpdateDTO janelasHorarioUpdateDTO) {
       JanelasHorario janelasHorarioAntiga = janelasHorarioRepository.findById(id).orElseThrow(()->new RuntimeException("Janela de Horário com esse id não foi encontrada"));
       janelasHorarioAntiga.setHoraInicio(janelasHorarioUpdateDTO.horaInicio());
       janelasHorarioAntiga.setHoraFim(janelasHorarioUpdateDTO.horaFim());
       JanelasHorario janelasHorarioAtualizada = janelasHorarioRepository.save(janelasHorarioAntiga);
       return transformarEmJanelasHorarioResponseDTO(janelasHorarioAtualizada);
    }
}
