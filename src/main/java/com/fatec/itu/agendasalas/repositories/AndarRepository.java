package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Andar;

@Repository
public interface AndarRepository extends JpaRepository<Andar, Long> {
    boolean existsByNome(String nome);
}
