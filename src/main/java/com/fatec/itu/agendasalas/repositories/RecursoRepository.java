package com.fatec.itu.agendasalas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fatec.itu.agendasalas.entity.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByTipoRecursoId(Long tipoId);
}
