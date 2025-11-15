package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsBySalaIdAndDataAndJanelasHorarioId(Long salaId, LocalDate data, Long janelaId);
    List<Agendamento> findAllByData(LocalDate data);
}   
