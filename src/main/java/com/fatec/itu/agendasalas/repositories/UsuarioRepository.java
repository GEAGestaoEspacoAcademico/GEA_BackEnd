package com.fatec.itu.agendasalas.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.itu.agendasalas.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    boolean existsByEmailAndIdNot(String email, Long id);

    public Usuario findByLogin(String login);
    

    
}
