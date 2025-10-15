package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PasswordEncoder cryptPasswordEncoder;
    
    public Usuario cadastrarUsuario(Usuario usuario){
       
        String senhaCriptografada = cryptPasswordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);
        Cargo cargo = cargoRepository.findByNome("USER").orElseThrow(()-> new RuntimeException("CARGO USER NÃO ENCONTRADO"));
        usuario.setCargo(cargo);

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosResponseDTO =  new ArrayList<>(); 
        for(Usuario usuario :listaUsuarios){
            UsuarioResponseDTO usuarioResponseDTO = conversaoUsuarioParaDTO(usuario); 
            listaUsuariosResponseDTO.add(usuarioResponseDTO);
        }
        return listaUsuariosResponseDTO;
    }
    
    private UsuarioResponseDTO conversaoUsuarioParaDTO(Usuario usuario){
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setCargoId(usuario.getCargo().getId());  
        return responseDTO;
    }

    public UsuarioResponseDTO buscarUsuarioPorId(long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
        UsuarioResponseDTO responseDTO = conversaoUsuarioParaDTO(usuario)
        return conversaoUsuarioParaDTO(usuario);
        
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
