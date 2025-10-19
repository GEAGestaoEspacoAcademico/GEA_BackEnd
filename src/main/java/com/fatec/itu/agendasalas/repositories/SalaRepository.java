package com.fatec.itu.agendasalas.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
  public List<Sala> findByDisponibilidade(boolean disponibilidade);
}
