package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.JanelasHorario;

@Repository
public interface JanelasHorarioRepository extends JpaRepository<JanelasHorario, Long> {
    @Query("""
        SELECT j
        FROM JanelasHorario j
        WHERE j.id NOT IN (
            SELECT a.janelasHorario.id
            FROM Agendamento a
            WHERE a.data = :data
        )
    """)
    List<JanelasHorario> findDisponiveisPorData(@Param("data") LocalDate data);

}
