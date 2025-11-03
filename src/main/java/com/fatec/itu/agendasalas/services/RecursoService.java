package com.fatec.itu.agendasalas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.recursos.RecursoCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoResponseDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoResumidoDTO;
import com.fatec.itu.agendasalas.dto.salas.SalaListDTO;
import com.fatec.itu.agendasalas.entity.Recurso;
import com.fatec.itu.agendasalas.entity.TipoRecurso;
import com.fatec.itu.agendasalas.repositories.RecursoRepository;
import com.fatec.itu.agendasalas.repositories.TipoRecursoRepository;

@Service
public class RecursoService {
  @Autowired
  RecursoRepository recursoRepository;
  
  @Autowired
  TipoRecursoRepository tipoRecursoRepository;

  public RecursoCompletoDTO buscarPorId(Long id) {
    Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return transformarRecursoEmRecursoDTO(recurso);
  }

  private RecursoCompletoDTO transformarRecursoEmRecursoDTO(Recurso recurso) {
    return new RecursoCompletoDTO(recurso.getId(), recurso.getNome(), recurso.getTipoRecurso().getId());
  } 
  
  public List<RecursoResponseDTO> listarTodosOsRecursos() {
    return recursoRepository.findAll().stream()
    .map(recurso -> new RecursoResponseDTO(recurso.getId(), recurso.getNome(), recurso.getTipoRecurso().getNome()))
    .toList();
  }

  @Transactional
  public RecursoCompletoDTO criar(RecursoResumidoDTO recursoDTO) {
      TipoRecurso tipo = tipoRecursoRepository.findById(recursoDTO.tipoId())
          .orElseThrow(() -> new RuntimeException("Tipo de recurso não encontrado"));

      Recurso novoRecurso = new Recurso(recursoDTO.nome(), tipo);
      Recurso recursoSalvo = recursoRepository.save(novoRecurso);

      return transformarRecursoEmRecursoDTO(recursoSalvo);
  }

  @Transactional
  public RecursoCompletoDTO atualizar(Long id, RecursoResumidoDTO recursoDTO) {
      Recurso recursoExistente = recursoRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

      TipoRecurso tipo = tipoRecursoRepository.findById(recursoDTO.tipoId())
          .orElseThrow(() -> new RuntimeException("Tipo de recurso não encontrado"));

      recursoExistente.setNome(recursoDTO.nome());
      recursoExistente.setTipoRecurso(tipo);

      Recurso recursoSalvo = recursoRepository.save(recursoExistente);
      return transformarRecursoEmRecursoDTO(recursoSalvo);
  }

  @Transactional
  public void deletar(Long id) {
    if (!recursoRepository.existsById(id)) {
      throw new RuntimeException();
    }
    recursoRepository.deleteById(id);
  }
}
