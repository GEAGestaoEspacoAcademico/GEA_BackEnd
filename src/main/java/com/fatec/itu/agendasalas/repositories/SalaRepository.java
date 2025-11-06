package com.fatec.itu.agendasalas.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fatec.itu.agendasalas.entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

        @Query(value = "SELECT SALA_ID FROM AGENDAMENTOS A "
                        + "JOIN JANELAS_HORARIO J ON A.JANELA_HORARIO_ID = J.ID "
                        + "WHERE ( :horaInicioParam < J.HORA_FIM) "
                        + "AND ( :horaFimParam > J.HORA_INICIO) "
                        + "AND :data BETWEEN A.DATA_INICIO AND A.DATA_FIM", nativeQuery = true)
        List<Long> findByDataEHorario(@Param("data") LocalDate data,
                        @Param("horaInicioParam") LocalTime horaInicio,
                        @Param("horaFimParam") LocalTime horaFim);

        public List<Sala> findByDisponibilidade(boolean disponibilidade);

        @Query("SELECT DISTINCT s FROM Sala s LEFT JOIN FETCH s.recursos rs "
                        + "LEFT JOIN FETCH rs.recurso r WHERE s.id IN :ids")
        List<Sala> findSalasComRecursosByIds(@Param("ids") List<Long> ids);
}
