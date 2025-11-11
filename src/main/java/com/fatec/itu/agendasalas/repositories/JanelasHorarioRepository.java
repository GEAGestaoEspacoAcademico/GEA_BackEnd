package com.fatec.itu.agendasalas.repositories;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.JanelasHorario;

@Repository
public interface JanelasHorarioRepository extends JpaRepository<JanelasHorario, Long> {

    
    JanelasHorario findByHoraInicioAndHoraFim(LocalTime horaInicio, LocalTime horaFim);
}
