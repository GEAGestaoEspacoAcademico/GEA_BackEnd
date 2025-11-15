package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.Recorrencia;

public interface RecorrenciaRepository extends JpaRepository<Recorrencia, Long> {
}