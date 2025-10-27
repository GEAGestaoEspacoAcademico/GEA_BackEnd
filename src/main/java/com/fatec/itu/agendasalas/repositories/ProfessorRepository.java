package com.fatec.itu.agendasalas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> deleteByRegistroProfessor(Long registroProfessor);
}
