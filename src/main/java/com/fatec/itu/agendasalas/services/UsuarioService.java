package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    
    public Usuario cadastrarUsuario(Usuario usuario){
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    
    public void atualizarUsuario(Usuario usuario, long id){
        Usuario auxiliar = usuarioRepository.getReferenceById(id);
        auxiliar.setEmail(usuario.getEmail());
        auxiliar.setNome(usuario.getNome());
        auxiliar.setLogin(usuario.getLogin());

        usuarioRepository.save(auxiliar);
    }
}
