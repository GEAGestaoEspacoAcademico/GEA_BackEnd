package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Map;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository, PasswordEncoder cryptPasswordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }
    
    private UsuarioRepository usuarioRepository;
    private CargoRepository cargoRepository;
    private PasswordEncoder cryptPasswordEncoder;
    
    public Usuario cadastrarUsuario(Usuario usuario){
       
        String senhaCriptografada = cryptPasswordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);
        Cargo cargo = cargoRepository.findByNome("USER").orElseThrow(()-> new RuntimeException("CARGO USER NÃO ENCONTRADO"));
        usuario.setCargo(cargo);

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
