package com.fatec.itu.agendasalas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.recursos.RecursoCompletoDTO;
import com.fatec.itu.agendasalas.dto.recursos.RecursoResumidoDTO;
import com.fatec.itu.agendasalas.entity.Recurso;
import com.fatec.itu.agendasalas.repositories.RecursoRepository;

@Service
public class RecursoService {
  @Autowired
  RecursoRepository recursoRepository;

  public RecursoCompletoDTO buscarPorId(Long id) {
    Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return transformarRecursoEmRecursoDTO(recurso);
  }

  private RecursoCompletoDTO transformarRecursoEmRecursoDTO(Recurso recurso) {
    return new RecursoCompletoDTO(recurso.getId(), recurso.getNome(), recurso.getTipo());
  }

  public List<RecursoCompletoDTO> listarTodosOsRecursos() {
    return recursoRepository.findAll().stream().map(this::transformarRecursoEmRecursoDTO).toList();
  }

  @Transactional
  public RecursoCompletoDTO criar(RecursoResumidoDTO recursoDTO) {
    Recurso novoRecurso = new Recurso(recursoDTO.nome(), recursoDTO.tipo());

    Recurso recursoSalvo = recursoRepository.save(novoRecurso);

    return transformarRecursoEmRecursoDTO(recursoSalvo);
  }

  @Transactional
  public RecursoCompletoDTO atualizar(Long id, RecursoResumidoDTO recursoDTO) {
    Recurso recursoExistente =
        recursoRepository.findById(id).orElseThrow(() -> new RuntimeException());

    recursoExistente.setNome(recursoDTO.nome());
    recursoExistente.setTipo(recursoDTO.tipo());

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
