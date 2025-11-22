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

import com.fatec.itu.agendasalas.dto.recursos.RecursoSalaUpdateQuantidadeDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaIndividualCreationDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListaCreationDTO;
import com.fatec.itu.agendasalas.dto.recursosSalasDTO.RecursoSalaListagemRecursosDTO;
import com.fatec.itu.agendasalas.dto.salas.RequisicaoDeSalaDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaDetailDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaPontuadaDTO;
import com.fatec.itu.agendasalas.entity.Andar;
import com.fatec.itu.agendasalas.entity.Recurso;
import com.fatec.itu.agendasalas.entity.RecursoSala;
import com.fatec.itu.agendasalas.entity.RecursoSalaId;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.TipoSala;
import com.fatec.itu.agendasalas.exceptions.RecursoJaAdicionadoNaSalaException;
import com.fatec.itu.agendasalas.repositories.AndarRepository;
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

    @Autowired
    private AndarRepository andarRepository;

    public SalaDetailDTO buscarPorId(Long id) {
      Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
      return transformarSalaEmSalaDetailDTO(salaExistente);
    }

    private SalaDetailDTO transformarSalaEmSalaDetailDTO(Sala sala) {

      return new SalaDetailDTO(
          sala.getId(),
          sala.getNome(),
          sala.getCapacidade(),
          sala.isDisponibilidade(),
          sala.getTipoSala() != null ? sala.getTipoSala().getId() : null,
          sala.getTipoSala() != null ? sala.getTipoSala().getNome() : null,
          sala.getAndar() != null ? sala.getAndar().getId() : null,
          sala.getAndar() != null ? sala.getAndar().getNome() : null,
          sala.getObservacoes()
      );
    }

    public List<SalaDetailDTO> listarSalasDisponiveis() {
      return salaRepository.findByDisponibilidade(true)
      .stream()
      .map(this::transformarSalaEmSalaDetailDTO)
      .toList();

    }

  public List<SalaDetailDTO> listarTodasAsSalas() {
    return salaRepository.findAll()
      .stream()
      .map(this::transformarSalaEmSalaDetailDTO)
      .toList();
  }


  public List<SalaDetailDTO> recomendacaoDeSala(RequisicaoDeSalaDTO requisicao) {
    List<Long> salasParaExcluir =
        salaRepository.findByDataEHorario(requisicao.data(), requisicao.horarios().horaInicio(),
            requisicao.horarios().horaFim(), requisicao.capacidade());

    List<SalaDetailDTO> salasCandidatas = listarTodasAsSalas().stream()
        .filter(sala -> !salasParaExcluir.contains(sala.salaId())).toList();

    List<Long> idsDeSalasCandidatas = salasCandidatas.stream().map(sala -> sala.salaId()).toList();

    List<Sala> salasComSeusRecursos =
        salaRepository.findSalasComRecursosByIds(idsDeSalasCandidatas);

    Map<Long, Set<Long>> mapaDasSalasESeusRecursos = salasComSeusRecursos.stream()
            .collect(Collectors.toMap(sala -> sala.getId(), sala -> sala.getRecursos().stream()
            .map(recursoSala -> recursoSala.getRecurso().getId()).collect(Collectors.toSet())));

    List<SalaPontuadaDTO> rankingSalas = new ArrayList<>();

    String nomeDoTipoSala = tipoSalaService.buscarPorId(requisicao.tipoSalaId()).getNome();

    for (SalaDetailDTO sala : salasCandidatas) {
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
    TipoSala tipoSala = tipoSalaService.buscarPorId(salaDTO.tipoSalaId());
    
    Andar andar = andarRepository.findById(salaDTO.andarId())
            .orElseThrow(() -> new RuntimeException("Andar não encontrado"));

    Sala novaSala = new Sala(
            salaDTO.salaNome(),
            salaDTO.salaCapacidade(),
            tipoSala,
            andar
    );

    novaSala.setDisponibilidade(salaDTO.disponibilidade());
    novaSala.setObservacoes(salaDTO.salaObservacoes());

    Sala salaSalva = salaRepository.save(novaSala);

    return transformarSalaEmSalaDetailDTO(salaSalva);
  }


  @Transactional
  public SalaDetailDTO atualizar(Long id, SalaCreateAndUpdateDTO salaDTO) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());

    if (salaDTO.salaNome() != null) {
      salaExistente.setNome(salaDTO.salaNome());
    }

    if (salaDTO.salaCapacidade() != null) {
      salaExistente.setCapacidade(salaDTO.salaCapacidade());
    }

    if (salaDTO.andarId() != null) {
      Andar andar = andarRepository.findById(salaDTO.andarId())
            .orElseThrow(() -> new RuntimeException("Andar não encontrado"));
      salaExistente.setAndar(andar);
    }

    if (salaDTO.disponibilidade() != null) {
      salaExistente.setDisponibilidade(salaDTO.disponibilidade());
    }

    if (salaDTO.tipoSalaId() != null) {
      salaExistente.setTipoSala(tipoSalaService.buscarPorId(salaDTO.tipoSalaId()));
    }

    if (salaDTO.salaObservacoes() != null) {
      salaExistente.setObservacoes(salaDTO.salaObservacoes());
    }

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
  public void adicionarRecurso(Long salaId, RecursoSalaListaCreationDTO dto) {
    Sala salaExistente = salaRepository.findById(salaId).orElseThrow(() -> new RuntimeException());

    for(RecursoSalaIndividualCreationDTO recurso : dto.listaDeRecursosParaAdicionar()){
        Recurso recursoExistente = recursoRepository.findById(recurso.recursoId())
            .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));

        boolean recursoJaAdicionado = salaExistente.getRecursos().stream()
                .anyMatch(rs -> rs.getRecurso().getId().equals(recurso.recursoId()));

            if (recursoJaAdicionado) {
                throw new RecursoJaAdicionadoNaSalaException(recurso.recursoId(), salaId);
            }

        RecursoSala novoLink = new RecursoSala();
        RecursoSalaId chaveCompostaId = new RecursoSalaId(recursoExistente.getId(), salaId);

        novoLink.setId(chaveCompostaId);
        novoLink.setQuantidade(recurso.quantidadeRecurso());
        novoLink.setSala(salaExistente);
        novoLink.setRecurso(recursoExistente);
        recursoSalaRepository.save(novoLink);

          
    }
    
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
  public void atualizarQuantidade(Long salaId, Long recursoId,
      RecursoSalaUpdateQuantidadeDTO dto) {
    Sala sala = salaRepository.findById(salaId)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

    RecursoSala linkParaAtualizar = sala.getRecursos().stream()
        .filter(rs -> rs.getRecurso().getId().equals(recursoId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Recurso não encontrado nesta sala!"));

    linkParaAtualizar.setQuantidade(dto.quantidade());

    salaRepository.save(sala);

  }

  public List<RecursoSalaListagemRecursosDTO> listarRecursosPorSala(Long id) {
    Sala salaExistente = salaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    List<RecursoSala> recursoNaSala = recursoSalaRepository.findBySalaId(salaExistente.getId());
    return recursoNaSala.stream()
        .map(recurso -> new RecursoSalaListagemRecursosDTO(
            recurso.getRecurso().getId(),
            recurso.getRecurso().getNome(), 
            recurso.getRecurso().getTipoRecurso().getNome(),
            recurso.getQuantidade()))
        .toList();
  }
}
