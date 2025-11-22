package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Secretaria;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {

    public boolean existsByMatricula(Long matricula);

}
