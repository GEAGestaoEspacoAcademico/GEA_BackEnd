package com.fatec.itu.agendasalas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findByEvento(Evento evento);
}