package com.fatec.itu.agendasalas.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationResponseDTO;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder cryptPasswordEncoder;

    public UsuarioAuthenticationResponseDTO login(UsuarioAuthenticationDTO usuarioAuthDTO){
        Usuario user = usuarioRepository.findByLogin(usuarioAuthDTO.usuarioLogin());
        
        //autenticação bem vagabunda, mas só pra dar tempo de ter um login no sistema
        //a autenticação certa usa o JWT Token
        if(user!=null && cryptPasswordEncoder.matches(usuarioAuthDTO.usuarioLogin(), user.getSenha())){
            String cargoNome = (user.getCargo() != null) ? user.getCargo().getNome() : null;
            return new UsuarioAuthenticationResponseDTO(user.getId(), user.getNome(), cargoNome);
        }
        throw new RuntimeException("Erro ao validar senha");
    }

 
}
