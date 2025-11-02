package com.fatec.itu.agendasalas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaResumidoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaListDTO;
import com.fatec.itu.agendasalas.entity.Recurso;
import com.fatec.itu.agendasalas.entity.RecursoSala;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.repositories.RecursoRepository;
import com.fatec.itu.agendasalas.repositories.RecursoSalaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;

@Service
public class SalaService {
  @Autowired
  private SalaRepository salaRepository;

  @Autowired
  private RecursoRepository recursoRepository;

  @Autowired
  private RecursoSalaRepository recursoSalaRepository;

  @Autowired
  private TipoSalaService tipoSalaService;

  public SalaDetailDTO buscarPorId(Long id) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return transformarSalaEmSalaDetailDTO(salaExistente);
  }

  private SalaDetailDTO transformarSalaEmSalaDetailDTO(Sala sala) {

    return new SalaDetailDTO(sala.getId(), sala.getNome(), sala.getCapacidade(), sala.getPiso(),
        sala.isDisponibilidade(), sala.getTipoSala().getNome(), sala.getObservacoes());
  }


  public List<SalaListDTO> listarTodasAsSalas() {
    return salaRepository.findAll().stream()
        .map(sala -> new SalaListDTO(sala.getId(), sala.getNome(), sala.getCapacidade(),
            sala.getPiso(), sala.isDisponibilidade(), sala.getTipoSala().getNome()))
        .toList();
  }

  @Transactional
  public SalaDetailDTO criar(SalaCreateAndUpdateDTO salaDTO) {
    Sala novaSala = new Sala(salaDTO.nome(), salaDTO.capacidade(), salaDTO.piso(),
        tipoSalaService.buscarPorId(salaDTO.idTipoSala()));
    novaSala.setObservacoes(salaDTO.observacoes());

    Sala salaSalva = salaRepository.save(novaSala);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }

  @Transactional
  public SalaDetailDTO atualizar(Long id, SalaCreateAndUpdateDTO salaDTO) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());

    salaExistente.setNome(salaDTO.nome());
    salaExistente.setCapacidade(salaDTO.capacidade());
    salaExistente.setPiso(salaDTO.piso());
    salaExistente.setDisponibilidade(salaDTO.disponibilidade());
    salaExistente.setTipoSala(tipoSalaService.buscarPorId(salaDTO.idTipoSala()));
    salaExistente.setObservacoes(salaDTO.observacoes());

    return transformarSalaEmSalaDetailDTO(salaRepository.save(salaExistente));
  }

  @Transactional
  public void deletar(Long id) {
    if (!salaRepository.existsById(id)) {
      throw new RuntimeException();
    }
    salaRepository.deleteById(id);
  }

  @Transactional
  public RecursoSalaCompletoDTO adicionarRecurso(Long salaId, RecursoSalaResumidoDTO dto) {
    Sala salaExistente = salaRepository.findById(salaId).orElseThrow(() -> new RuntimeException());

    Recurso recursoExistente = recursoRepository.findById(dto.idRecurso())
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));

    boolean recursoJaAdicionado = salaExistente.getRecursos().stream()
        .anyMatch(rs -> rs.getRecurso().getId().equals(dto.idRecurso()));

    if (recursoJaAdicionado) {
      throw new RuntimeException();
    }

    RecursoSala novoLink = new RecursoSala();

    novoLink.setIdRecurso(recursoExistente.getId());
    novoLink.setIdSala(salaId);
    novoLink.setQuantidade(dto.quantidade());

    recursoSalaRepository.save(novoLink);

    return new RecursoSalaCompletoDTO(dto.idRecurso(), recursoExistente.getNome(),
        recursoExistente.getTipo(), dto.quantidade());
  }

  @Transactional
  public void removerRecurso(Long salaId, Long recursoId) {
    Sala sala = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

    RecursoSala linkParaRemover = sala.getRecursos().stream()
        .filter(rs -> rs.getRecurso().getId().equals(recursoId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado nesta sala!"));

    sala.getRecursos().remove(linkParaRemover);

    recursoSalaRepository.delete(linkParaRemover);
  }

  @Transactional
  public RecursoSalaCompletoDTO atualizarQuantidade(Long salaId, Long recursoId,
      RecursoSalaUpdateQuantidadeDTO dto) {
    Sala sala = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

    RecursoSala linkParaAtualizar = sala.getRecursos().stream()
        .filter(rs -> rs.getRecurso().getId().equals(recursoId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado nesta sala!"));

    linkParaAtualizar.setQuantidade(dto.quantidade());

    salaRepository.save(sala);

    return new RecursoSalaCompletoDTO(linkParaAtualizar.getRecurso().getId(),
        linkParaAtualizar.getRecurso().getNome(), linkParaAtualizar.getRecurso().getTipo(),
        linkParaAtualizar.getQuantidade());
  }

  public List<RecursoSalaCompletoDTO> listarRecursosPorSala(Long id) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    List<RecursoSala> recursoNaSala = recursoSalaRepository.findByIdSala(salaExistente.getId());
    return recursoNaSala.stream()
        .map(recurso -> new RecursoSalaCompletoDTO(recurso.getRecurso().getId(),
            recurso.getRecurso().getNome(), recurso.getRecurso().getTipo(),
            recurso.getQuantidade()))
        .toList();
  }
}
