package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.AgendamentoCancelado;

public interface AgendamentoCanceladoRepository extends JpaRepository<AgendamentoCancelado, Long> {
}

