package com.fatec.itu.agendasalas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.itu.agendasalas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
