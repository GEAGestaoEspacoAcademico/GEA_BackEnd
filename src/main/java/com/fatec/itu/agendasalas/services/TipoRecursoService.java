package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.tipoRecurso.TipoRecursoListDTO;
import com.fatec.itu.agendasalas.entity.TipoRecurso;
import com.fatec.itu.agendasalas.repositories.TipoRecursoRepository;

@Service
public class TipoRecursoService {
@Autowired
TipoRecursoRepository tipoRecursoRepository;

  public TipoRecursoListDTO buscarPorIdRetornarDTO(Long id) {
    return converterParaDTO(tipoRecursoRepository.findById(id).orElseThrow());
  }

  public TipoRecurso buscarPorId(Long id) {
    return tipoRecursoRepository.findById(id).orElseThrow();
  }

  private TipoRecursoListDTO converterParaDTO(TipoRecurso tipoRecurso) {
    return new TipoRecursoListDTO(tipoRecurso.getId(), tipoRecurso.getNome());
  }

  public List<TipoRecursoListDTO> listarTodos() {
    List<TipoRecurso> tiposRecurso = tipoRecursoRepository.findAll();
    List<TipoRecursoListDTO> tiposRecursoDTO = new ArrayList<>();

    for (TipoRecurso TipoRecurso : tiposRecurso) {
      tiposRecursoDTO.add(converterParaDTO(TipoRecurso));
    }

    return tiposRecursoDTO;
  }

  public long criarTipoRecurso(TipoRecursoCreateAndUpdateDTO dto) {
      TipoRecurso novoTipoRecurso = new TipoRecurso();
      novoTipoRecurso.setNome(dto.tipoRecursoNome());

      TipoRecurso salvo = tipoRecursoRepository.save(novoTipoRecurso);
      return salvo.getId(); // retorna apenas o ID
  }

  public TipoRecursoListDTO atualizar(Long id, TipoRecursoCreateAndUpdateDTO TipoRecurso) {

    TipoRecurso TipoRecursoAtualizado = buscarPorId(id);
    TipoRecursoAtualizado.setNome(TipoRecurso.tipoRecursoNome());

    return converterParaDTO(tipoRecursoRepository.save(TipoRecursoAtualizado));
  }

  public void deletar(Long id) {
    if (!tipoRecursoRepository.existsById(id)) {
      throw new RuntimeException();
    }
    tipoRecursoRepository.deleteById(id);
  }
}
