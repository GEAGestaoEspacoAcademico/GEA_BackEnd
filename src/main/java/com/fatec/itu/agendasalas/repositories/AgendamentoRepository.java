package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
<<<<<<< HEAD

    boolean existsBySalaIdAndDataAndJanelasHorarioId(Long salaId, LocalDate data, Long janelaId);
=======
    @Query("SELECT a.id FROM Agendamento a "
            + "WHERE a.data = :data AND a.janelasHorario.id IN (:janelas)"
            + " AND a.sala.id = :sala_id")
    List<Long> findByDataAndJanelaHorario(@Param("data") LocalDate data,
            @Param("janelas") List<Long> janelas, @Param("sala_id") Long salaId);
>>>>>>> 75eeeb7bee6fefd04da84f4d9e173d8f47b76f36
    List<Agendamento> findAllByData(LocalDate data);
}   
