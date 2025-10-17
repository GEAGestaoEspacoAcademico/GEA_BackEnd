package com.fatec.itu.agendasalas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> deleteByRegistroProfessor(Long registroProfessor);
}
