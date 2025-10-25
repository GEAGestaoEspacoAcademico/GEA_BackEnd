package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    
}
