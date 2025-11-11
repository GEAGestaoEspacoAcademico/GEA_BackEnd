package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.AgendamentoEvento;

public interface AgendamentoEventoRepository extends JpaRepository<AgendamentoEvento, Long>{
    
}
