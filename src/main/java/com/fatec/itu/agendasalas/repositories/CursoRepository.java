package com.fatec.itu.agendasalas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
  @Query("SELECT c FROM Disciplina d JOIN d.curso c WHERE d.professor.id=:idProfessor")
  List<Curso> findCursosByProfessorRegistro(@Param("idProfessor") Long idProfessor);

  Optional<Curso> findBySigla(String sigla);

  Curso findByCoordenadorId(Long coordenadorId);

}