package com.fatec.itu.agendasalas.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationResponseDTO;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder cryptPasswordEncoder;


    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        return repository.findByLogin(login);
    }    

    // public UsuarioAuthenticationResponseDTO login(UsuarioAuthenticationDTO usuarioAuthDTO){
    //     Usuario user = usuarioRepository.findByLogin(usuarioAuthDTO.getLogin());
        
    //     //autenticação bem vagabunda, mas só pra dar tempo de ter um login no sistema
    //     //a autenticação certa usa o JWT Token
    //     if(user!=null && cryptPasswordEncoder.matches(usuarioAuthDTO.getSenha(), user.getSenha())){
    //         String cargoNome = (user.getCargo() != null) ? user.getCargo().getNome() : null;
    //         return new UsuarioAuthenticationResponseDTO(user.getNome(), user.getId(), cargoNome);
    //     }
    //     throw new RuntimeException("Erro ao validar senha");
    // }

 
}
