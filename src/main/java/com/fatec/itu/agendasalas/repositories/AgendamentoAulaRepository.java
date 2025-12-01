package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;

@Repository
public interface AgendamentoAulaRepository extends JpaRepository<AgendamentoAula, Long> {

    // Buscar agendamentos de aula por disciplina
    @Query("SELECT a FROM AgendamentoAula a WHERE a.disciplina.id = :disciplinaId")
    List<AgendamentoAula> findByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);

    // Buscar agendamentos de aula por professor (através da disciplina)
    @Query("SELECT a FROM AgendamentoAula a WHERE a.disciplina.professor.id = :professorId")
    List<AgendamentoAula> findByProfessorId(@Param("professorId") Integer professorId);

    AgendamentoAula findBySalaIdAndDataAndJanelasHorarioId(Long salaId, LocalDate data, Long janelaId);

    boolean existsBySalaIdAndDataAndJanelasHorarioIdAndProfessorId(Long salaId, LocalDate data, Long janelaId, Long professorId);
}
