package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.TipoSala;

@Repository
public interface TipoSalaRepository extends JpaRepository<TipoSala, Long> {

}
