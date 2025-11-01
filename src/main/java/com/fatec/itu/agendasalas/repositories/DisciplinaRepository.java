package com.fatec.itu.agendasalas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.Professor;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
  public List<Disciplina> findByProfessorId(Long idProfessor);
  public List<Disciplina> findByProfessor(Professor professor);
}
