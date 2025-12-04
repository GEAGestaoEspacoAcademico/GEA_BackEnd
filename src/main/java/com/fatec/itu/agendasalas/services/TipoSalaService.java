package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.tiposSalas.TipoSalaCreateAndUpdateDTO;
import com.fatec.itu.agendasalas.dto.tiposSalas.TipoSalaListDTO;
import com.fatec.itu.agendasalas.entity.TipoSala;
import com.fatec.itu.agendasalas.repositories.TipoSalaRepository;

@Service
public class TipoSalaService {
  @Autowired
  TipoSalaRepository tipoSalaRepository;

  public TipoSalaListDTO buscarPorIdRetornarDTO(Long id) {
    return converterParaDTO(tipoSalaRepository.findById(id).orElseThrow());
  }

  public TipoSala buscarPorId(Long id) {
    return tipoSalaRepository.findById(id).orElseThrow();
  }

  private TipoSalaListDTO converterParaDTO(TipoSala tipoSala) {
    return new TipoSalaListDTO(tipoSala.getId(), tipoSala.getNome());
  }

  public List<TipoSalaListDTO> listarTodos() {
    List<TipoSala> tiposSalas = tipoSalaRepository.findAll();
    List<TipoSalaListDTO> tiposSalasDTO = new ArrayList<>();

    for (TipoSala tipoSala : tiposSalas) {
      tiposSalasDTO.add(converterParaDTO(tipoSala));
    }

    return tiposSalasDTO;
  }

  public long criarTipoSala(TipoSalaCreateAndUpdateDTO dto) {
      TipoSala novoTipoSala = new TipoSala();
      novoTipoSala.setNome(dto.tipoSalaNome());

      TipoSala salvo = tipoSalaRepository.save(novoTipoSala);
      return salvo.getId(); 
  }


  public TipoSalaListDTO atualizar(Long id, TipoSalaCreateAndUpdateDTO tipoSala) {

    TipoSala tipoSalaAtualizado = buscarPorId(id);
    tipoSalaAtualizado.setNome(tipoSala.tipoSalaNome());

    return converterParaDTO(tipoSalaRepository.save(tipoSalaAtualizado));
  }

  public void deletar(Long id) {
    if (!tipoSalaRepository.existsById(id)) {
      throw new RuntimeException();
    }
    tipoSalaRepository.deleteById(id);
  }
}
