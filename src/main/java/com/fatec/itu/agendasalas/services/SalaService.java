package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaResumidoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.salas.RequisicaoDeSalaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaListDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaPontuadaDTO;
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

  public List<SalaListDTO> listarSalasDisponiveis(boolean disponivel) {
    return salaRepository.findByDisponibilidade(disponivel).stream()
        .map(sala -> new SalaListDTO(sala.getId(), sala.getNome(), sala.getCapacidade(),
            sala.getPiso(), sala.isDisponibilidade(), sala.getTipoSala().getNome()))
        .toList();
  }

  public List<SalaListDTO> listarSalasDisponiveis() {
    return transformaEmSalaListDTO(salaRepository.findByDisponibilidade(true));
  }

  public List<SalaListDTO> listarTodasAsSalas() {
    return transformaEmSalaListDTO(salaRepository.findAll());
  }

  private List<SalaListDTO> transformaEmSalaListDTO(List<Sala> salas) {
    return salas.stream()
        .map(sala -> new SalaListDTO(sala.getId(), sala.getNome(), sala.getCapacidade(),
            sala.getPiso(), sala.isDisponibilidade(), sala.getTipoSala().getNome()))
        .toList();
  }

  public List<SalaListDTO> recomendacaoDeSala(RequisicaoDeSalaDTO requisicao) {
    List<Long> salasParaExcluir =
        salaRepository.findByDataEHorario(requisicao.data(), requisicao.horarios().horaInicio(),
            requisicao.horarios().horaFim(), requisicao.capacidade());

    List<SalaListDTO> salasCandidatas = listarTodasAsSalas().stream()
        .filter(sala -> !salasParaExcluir.contains(sala.salaId())).toList();

    List<Long> idsDeSalasCandidatas = salasCandidatas.stream().map(sala -> sala.salaId()).toList();

    List<Sala> salasComSeusRecursos =
        salaRepository.findSalasComRecursosByIds(idsDeSalasCandidatas);

    Map<Long, Set<Long>> mapaDasSalasESeusRecursos = salasComSeusRecursos.stream()
        .collect(Collectors.toMap(sala -> sala.getId(), sala -> sala.getRecursos().stream()
            .map(recursoSala -> recursoSala.getRecurso().getId()).collect(Collectors.toSet())));

    List<SalaPontuadaDTO> rankingSalas = new ArrayList<>();

    String nomeDoTipoSala = tipoSalaService.buscarPorId(requisicao.tipoSalaId()).getNome();

    for (SalaListDTO sala : salasCandidatas) {
      int pontuacao = 0;

      Set<Long> recursosDaSala =
          mapaDasSalasESeusRecursos.getOrDefault(sala.salaId(), Collections.emptySet());

      if (sala.tipoSala().equals(nomeDoTipoSala))
        pontuacao++;

      pontuacao +=
          requisicao.recursosIds().stream().filter(r -> recursosDaSala.contains(r)).count();

      if (pontuacao > 0)
        rankingSalas.add(new SalaPontuadaDTO(sala, pontuacao));
    }

    rankingSalas.sort(Comparator.comparing((SalaPontuadaDTO sala) -> sala.salaPontuacao()).reversed());

    return rankingSalas.stream().limit(5).map(SalaPontuadaDTO::sala).toList();
  }

  @Transactional
  public SalaDetailDTO criar(SalaCreateAndUpdateDTO salaDTO) {
    Sala novaSala = new Sala(salaDTO.salaNome(), salaDTO.salaCapacidade(), salaDTO.piso(),
        tipoSalaService.buscarPorId(salaDTO.tipoSalaId()));
    novaSala.setObservacoes(salaDTO.salaObservacoes());

    Sala salaSalva = salaRepository.save(novaSala);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }

  @Transactional
  public SalaDetailDTO atualizar(Long id, SalaCreateAndUpdateDTO salaDTO) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());

    salaExistente.setNome(salaDTO.salaNome());
    salaExistente.setCapacidade(salaDTO.salaCapacidade());
    salaExistente.setPiso(salaDTO.piso());
    salaExistente.setDisponibilidade(salaDTO.disponibilidade());
    salaExistente.setTipoSala(tipoSalaService.buscarPorId(salaDTO.tipoSalaId()));
    salaExistente.setObservacoes(salaDTO.salaObservacoes());

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

    Recurso recursoExistente = recursoRepository.findById(dto.recursoId())
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));

    boolean recursoJaAdicionado = salaExistente.getRecursos().stream()
        .anyMatch(rs -> rs.getRecurso().getId().equals(dto.recursoId()));

    if (recursoJaAdicionado) {
      throw new RuntimeException();
    }

    RecursoSala novoLink = new RecursoSala();

    novoLink.setIdRecurso(recursoExistente.getId());
    novoLink.setIdSala(salaId);
    novoLink.setQuantidade(dto.quantidadeRecurso());

    recursoSalaRepository.save(novoLink);

    return new RecursoSalaCompletoDTO(dto.recursoId(), recursoExistente.getNome(),
        recursoExistente.getTipoRecurso().getId(), dto.quantidadeRecurso());
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
        linkParaAtualizar.getRecurso().getNome(),
        linkParaAtualizar.getRecurso().getTipoRecurso().getId(), linkParaAtualizar.getQuantidade());
  }

  public List<RecursoSalaCompletoDTO> listarRecursosPorSala(Long id) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    List<RecursoSala> recursoNaSala = recursoSalaRepository.findByIdSala(salaExistente.getId());
    return recursoNaSala.stream()
        .map(recurso -> new RecursoSalaCompletoDTO(recurso.getRecurso().getId(),
            recurso.getRecurso().getNome(), recurso.getRecurso().getTipoRecurso().getId(),
            recurso.getQuantidade()))
        .toList();
  }
}
