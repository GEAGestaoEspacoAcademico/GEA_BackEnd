package com.fatec.itu.agendasalas.repositories;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.JanelasHorario;

@Repository
public interface JanelasHorarioRepository extends JpaRepository<JanelasHorario, Long> {

    @Query("SELECT j.id FROM JanelasHorario j WHERE j.horaInicio=:horaInicio AND j.horaFim=:horaFim")
    Long findIdByHoraInicioAndHoraFim(@Param("horaInicio") LocalTime horaInicio, @Param("horaFim") LocalTime horaFim);
}
