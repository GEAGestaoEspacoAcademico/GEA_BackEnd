package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> f50fa315d6066f88c2c09064bce1be3f4c9d6078

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
<<<<<<< HEAD

  
    boolean existsBySalaIdAndDataAndJanelasHorarioId(Long salaId, LocalDate data, Long janelaId);

=======
    List<Agendamento> findAllByData(LocalDate data);
>>>>>>> f50fa315d6066f88c2c09064bce1be3f4c9d6078
}
