package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Map;

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
    
    public void atualizarUsuario(Map<String, Object> usuario, long id){
        Usuario auxiliar = usuarioRepository.getReferenceById(id);
        
        if(usuario.containsKey("nome")) auxiliar.setNome((String) usuario.get("nome"));
        if(usuario.containsKey("email")) auxiliar.setEmail((String) usuario.get("email"));
        if(usuario.containsKey("login")) auxiliar.setLogin((String) usuario.get("login"));
        
        usuarioRepository.save(auxiliar);
    }


}
