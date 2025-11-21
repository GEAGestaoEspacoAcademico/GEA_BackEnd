package com.fatec.itu.agendasalas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.RecursoSala;
import com.fatec.itu.agendasalas.entity.RecursoSalaId;


public interface RecursoSalaRepository extends JpaRepository<RecursoSala, RecursoSalaId> {
  public List<RecursoSala> findBySalaId(Long salaId);
}
