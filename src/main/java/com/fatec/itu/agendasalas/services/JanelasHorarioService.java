package com.fatec.itu.agendasalas.services;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.format.DateTimeParseException;

import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioPorVariasDatasRequestDTO;
import com.fatec.itu.agendasalas.exceptions.janelasHorario.FormatoDataInvalidoException;
import com.fatec.itu.agendasalas.exceptions.janelasHorario.ListaDatasInvalidaException;
import com.fatec.itu.agendasalas.exceptions.janelasHorario.SalaIdObrigatorioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioCreationDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioResponseDTO;
import com.fatec.itu.agendasalas.dto.janelasHorario.JanelasHorarioUpdateDTO;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JanelasHorarioService {

    @Autowired
    private JanelasHorarioRepository janelasHorarioRepository;

    public List<JanelasHorarioResponseDTO> listarTodasJanelasHorario() {

        List<JanelasHorario> listaJanelasHorario = janelasHorarioRepository.findAll();
        List<JanelasHorarioResponseDTO> listaJanelasHorarioResponseDTOs = new ArrayList<JanelasHorarioResponseDTO>();
        for (JanelasHorario janela : listaJanelasHorario) {
            listaJanelasHorarioResponseDTOs.add(transformarEmJanelasHorarioResponseDTO(janela));
        }

        return listaJanelasHorarioResponseDTOs;
    }

    private JanelasHorarioResponseDTO transformarEmJanelasHorarioResponseDTO(JanelasHorario janelaHorario) {
        return new JanelasHorarioResponseDTO(janelaHorario.getId(), janelaHorario.getHoraInicio(),
                janelaHorario.getHoraFim());
    }

    @Transactional
    public JanelasHorarioResponseDTO criarJanelaHorario(JanelasHorarioCreationDTO janelasHorarioCreationDTO) {
        JanelasHorario janelasHorarioCreation = new JanelasHorario(janelasHorarioCreationDTO.horaInicio(),
                janelasHorarioCreationDTO.horaFim());
        JanelasHorario janelasHorarioSalvo = janelasHorarioRepository.save(janelasHorarioCreation);

        return transformarEmJanelasHorarioResponseDTO(janelasHorarioSalvo);
    }

    public JanelasHorarioResponseDTO filtrarJanelaHorarioPeloID(Long id) {
        JanelasHorario janelasHorario = janelasHorarioRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Janela de Horário com id " + id + " não foi encontrada"));
        return transformarEmJanelasHorarioResponseDTO(janelasHorario);
    }

    @Transactional
    public JanelasHorarioResponseDTO atualizarJanelasHorario(Long id, JanelasHorarioUpdateDTO janelasHorarioUpdateDTO) {
        JanelasHorario janelasHorarioAntiga = janelasHorarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Janela de Horário com id " + id + " não foi encontrada"));
        janelasHorarioAntiga.setHoraInicio(janelasHorarioUpdateDTO.horaInicio());
        janelasHorarioAntiga.setHoraFim(janelasHorarioUpdateDTO.horaFim());
        JanelasHorario janelasHorarioAtualizada = janelasHorarioRepository.save(janelasHorarioAntiga);
        return transformarEmJanelasHorarioResponseDTO(janelasHorarioAtualizada);
    }

    public List<JanelasHorario> buscaJanelaHorarioPelosHorariosInicioeFim(LocalTime horaInicio, LocalTime horaFim,
            boolean todosHorarios) {
        List<JanelasHorario> listaJanelas = new ArrayList<>();
        if (todosHorarios) {
            listaJanelas = janelasHorarioRepository.findAll();
        } else {
            while (horaInicio != horaFim) {
                JanelasHorario provisoria = janelasHorarioRepository.findByHoraInicio(horaInicio);
                listaJanelas.add(provisoria);
                horaInicio = provisoria.getHoraFim();
            }

        }

        return listaJanelas;
    }

    public List<JanelasHorario> buscarDisponiveisPorData(LocalDate data) {
        throw new UnsupportedOperationException("Use buscarDisponiveisPorData(data, salaId) com salaId");
    }

    public List<JanelasHorario> buscarDisponiveisPorData(LocalDate data, Long salaId) {
        return janelasHorarioRepository.findDisponiveisPorData(data, salaId);
    }

    public List<JanelasHorario> buscarDisponiveisEmVariasDatas(List<LocalDate> datas, Long salaId) {
        if (datas == null || datas.isEmpty()) {
            return new ArrayList<>();
        }

        List<JanelasHorario> primeiraLista = buscarDisponiveisPorData(datas.get(0), salaId);
        if (primeiraLista.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> idsComuns = new HashSet<>();
        for (JanelasHorario j : primeiraLista) {
            idsComuns.add(j.getId());
        }

        for (int i = 1; i < datas.size() && !idsComuns.isEmpty(); i++) {
            List<JanelasHorario> listaAtual = buscarDisponiveisPorData(datas.get(i), salaId);
            Set<Long> idsAtuais = new HashSet<>();
            for (JanelasHorario j : listaAtual) {
                idsAtuais.add(j.getId());
            }
            idsComuns.retainAll(idsAtuais);
        }

        List<JanelasHorario> resultado = new ArrayList<>();
        for (JanelasHorario j : primeiraLista) {
            if (idsComuns.contains(j.getId())) {
                resultado.add(j);
            }
        }

        return resultado;
    }

    public List<JanelasHorarioResponseDTO> buscarDisponiveisEmVariasDatas(JanelasHorarioPorVariasDatasRequestDTO datasRequest) {
        if (datasRequest == null || datasRequest.datas() == null || datasRequest.datas().isEmpty()) {
            throw new ListaDatasInvalidaException();
        }
        List<LocalDate> datas;
        try {
            datas = datasRequest.datas().stream()
                    .map(LocalDate::parse)
                    .toList();
        } catch (DateTimeParseException e) {
            throw new FormatoDataInvalidoException("Formato de data inválido. Use yyyy-MM-dd", e);
        }
        if (datasRequest.salaId() == null) {
            throw new SalaIdObrigatorioException();
        }

        List<JanelasHorario> janelas = buscarDisponiveisEmVariasDatas(datas, datasRequest.salaId());
        return janelas.stream()
                .map(this::transformarEmJanelasHorarioResponseDTO)
                .toList();
    }


    public List<JanelasHorarioResponseDTO> buscarDisponiveisPorDataDTO(LocalDate data, Long salaId) {
        List<JanelasHorario> lista = buscarDisponiveisPorData(data, salaId);
        return lista.stream()
                .map(this::transformarEmJanelasHorarioResponseDTO)
                .toList();
    }
}
