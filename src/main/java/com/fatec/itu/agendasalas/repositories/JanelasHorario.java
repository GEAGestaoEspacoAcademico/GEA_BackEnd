package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JanelasHorario extends JpaRepository<JanelasHorario, Long> {

}
