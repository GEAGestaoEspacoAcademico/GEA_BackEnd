package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
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
        ) order by j.id asc
    """)
    List<JanelasHorario> findDisponiveisPorData(@Param("data") LocalDate data);

<<<<<<< HEAD
    
    JanelasHorario findByHoraInicio(LocalTime horaInicio);
=======
    @Query("SELECT j from JanelasHorario j where j.horaInicio >= :horaInicio and j.horaFim <= :horaFim")
    List<JanelasHorario> findByIntervaloIdHorarios(@Param("horaInicio") LocalTime horaInicio, @Param("horaFim") LocalTime horaFim);
>>>>>>> 75eeeb7bee6fefd04da84f4d9e173d8f47b76f36
}
