package com.fatec.itu.agendasalas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long>  {
    Optional<Cargo> findByNome(String nome);
}
