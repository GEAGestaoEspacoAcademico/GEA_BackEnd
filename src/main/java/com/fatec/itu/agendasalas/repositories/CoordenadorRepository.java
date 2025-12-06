package com.fatec.itu.agendasalas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Coordenador;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    
    boolean existsByRegistroCoordenacao(Long registroCoordenacao);
    
    Optional<Coordenador> findByRegistroCoordenacao(Long registroCoordenacao);
    
}
