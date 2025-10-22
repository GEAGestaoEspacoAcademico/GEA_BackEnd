package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.RecursoCompletoDTO;
import com.fatec.itu.agendasalas.dto.RecursoDaSalaDTO;
import com.fatec.itu.agendasalas.dto.RecursoUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.SalaCreateDTO;
import com.fatec.itu.agendasalas.dto.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.SalaListDTO;
import com.fatec.itu.agendasalas.entity.Recurso;
import com.fatec.itu.agendasalas.entity.RecursoSala;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.repositories.RecursoRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;

@Service
public class SalaService {
  @Autowired
  SalaRepository salaRepository;

  @Autowired
  RecursoRepository recursoRepository;

  public SalaDetailDTO buscarPorId(Long id) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return transformarSalaEmSalaDetailDTO(salaExistente);
  }

  private SalaDetailDTO transformarSalaEmSalaDetailDTO(Sala sala) {
    List<RecursoDaSalaDTO> recursosDTO = new ArrayList<>();

    if (!sala.getRecursos().isEmpty()) {
      recursosDTO = sala.getRecursos().stream()
          .map(rs -> new RecursoDaSalaDTO(rs.getRecurso().getId(), rs.getQuantidade())).toList();
    }

    return new SalaDetailDTO(sala.getId(),
        sala.getNome(),
        sala.getCapacidade(),
        sala.getPiso(),
        sala.isDisponibilidade(), 
        sala.isLaboratorio(), 
        recursosDTO, 
        sala.getObservacoes());
  }

  public List<SalaListDTO> listarSalasDisponiveis() {
    return salaRepository.findByDisponibilidade(true).stream().map(sala -> new SalaListDTO(sala.getId(), sala.getNome(), sala.getCapacidade(), sala.isDisponibilidade())).toList();
  }
  
  public List<SalaListDTO> listarTodasAsSalas() {
    return salaRepository.findAll().stream().map(sala -> new SalaListDTO(sala.getId(), sala.getNome(), sala.getCapacidade(), sala.isDisponibilidade())).toList();
  }

  @Transactional
  public SalaDetailDTO criar(SalaCreateDTO salaDTO) {
    Sala novaSala =
        new Sala(salaDTO.nome(), salaDTO.capacidade(), salaDTO.piso(), salaDTO.isLaboratorio());
    
    if (!salaDTO.recursos().isEmpty()) {
      for (RecursoDaSalaDTO recursoDTO : salaDTO.recursos()) {
        Recurso recursoExistente =
            recursoRepository.findById(recursoDTO.id()).orElseThrow(() -> new RuntimeException());

        RecursoSala linkNoBancoEntreSalaERecurso =
            new RecursoSala(recursoExistente.getId(), novaSala.getId(), recursoDTO.quantidade());

        novaSala.getRecursos().add(linkNoBancoEntreSalaERecurso);
      }
    }

    Sala salaSalva = salaRepository.save(novaSala);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }

  @Transactional
  public void atualizar(SalaDetailDTO salaDTO) {
    Sala salaExistente =
        salaRepository.findById(salaDTO.id()).orElseThrow(() -> new RuntimeException());
    
    salaExistente.setNome(salaDTO.nome());
    salaExistente.setCapacidade(salaDTO.capacidade());
    salaExistente.setPiso(salaDTO.piso());
    salaExistente.setDisponibilidade(salaDTO.disponibilidade());
    salaExistente.setLaboratorio(salaDTO.isLaboratorio());
    salaExistente.setObservacoes(salaDTO.observacoes());

    salaRepository.save(salaExistente);
  }

  @Transactional
  public void deletar(Long id) {
    if (!salaRepository.existsById(id)) {
      throw new RuntimeException();
    }
    salaRepository.deleteById(id);
  }

  @Transactional
  public SalaDetailDTO adicionarRecurso(Long salaId, RecursoDaSalaDTO dto) {
    Sala salaExistente = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException());

    Recurso recursoExistente = recursoRepository.findById(dto.id())
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));

    boolean recursoJaAdicionado =
        salaExistente.getRecursos().stream().anyMatch(rs -> rs.getRecurso().getId().equals(dto.id()));
    
    if (recursoJaAdicionado) {
      throw new RuntimeException();
    }

    RecursoSala novoLink = new RecursoSala(recursoExistente.getId(), salaExistente.getId(), dto.quantidade());

    salaExistente.getRecursos().add(novoLink);

    Sala salaSalva = salaRepository.save(salaExistente);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }

  @Transactional
  public void removerRecurso(Long salaId, Long recursoId) {
    Sala sala = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

    RecursoSala linkParaRemover = sala.getRecursos().stream()
        .filter(rs -> rs.getRecurso().getId().equals(recursoId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado nesta sala!"));

    sala.getRecursos().remove(linkParaRemover);

    salaRepository.save(sala);
  }

  @Transactional
  public SalaDetailDTO atualizarQuantidade(Long salaId, Long recursoId, RecursoUpdateQuantidadeDTO dto) {
    Sala sala = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

    RecursoSala linkParaAtualizar = sala.getRecursos().stream()
        .filter(rs -> rs.getRecurso().getId().equals(recursoId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado nesta sala!"));

    linkParaAtualizar.setQuantidade(dto.quantidade());

    Sala salaSalva = salaRepository.save(sala);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }
}