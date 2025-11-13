package com.fatec.itu.agendasalas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {


    public Optional<Evento> findByNome(String nome);
}
