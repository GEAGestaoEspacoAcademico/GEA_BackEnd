package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    
    public Usuario buscarUsuarioPorId(long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
    }

    public void atualizarUsuario(Map<String, Object> usuario, long id){
        Usuario auxiliar = usuarioRepository.getReferenceById(id);
        
        if(usuario.containsKey("nome")) auxiliar.setNome((String) usuario.get("nome"));
        if(usuario.containsKey("email")) auxiliar.setEmail((String) usuario.get("email"));
        if(usuario.containsKey("login")) auxiliar.setLogin((String) usuario.get("login"));
        if(usuario.containsKey("cargo")){
            String cargoNome = (String)usuario.get("cargo");
            Optional<Cargo> cargoOptional = cargoRepository.findByNome(cargoNome);
            Cargo cargo = cargoOptional.orElseThrow(()->new RuntimeException("cargo nao encontrado"));
            auxiliar.setCargo(cargo); 
        }

        usuarioRepository.save(auxiliar);
    }


}
