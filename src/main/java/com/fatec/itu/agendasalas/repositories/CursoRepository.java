package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}

