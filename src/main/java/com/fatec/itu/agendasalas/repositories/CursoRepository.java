package com.fatec.itu.agendasalas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
  @Query("SELECT c FROM Disciplina d JOIN d.curso c WHERE d.professor.registroProfessor=:registroProfessor")
  List<Curso> findCursosByProfessorRegistro(@Param("registroProfessor") Long registroProfessor);
}