package com.fatec.itu.agendasalas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Coordenador;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    
    boolean existsByRegistroCoordenacao(int registroCoordenacao);
    
    Optional<Coordenador> findByRegistroCoordenacao(int registroCoordenacao);
    Optional<Coordenador> findByProfessorId(Long professorId);
}
