package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder cryptPasswordEncoder;

    @Autowired
    private CargoRepository cargoRepository;


       public UsuarioResponseDTO cadastrarUsuario(UsuarioCreationDTO usuarioDTO){
        
        Usuario usuario = new Usuario(usuarioDTO.usuarioLogin(), usuarioDTO.usuarioEmail(), usuarioDTO.usuarioNome());
        String senhaCriptografada = cryptPasswordEncoder.encode(usuarioDTO.usuarioSenha());
        usuario.setSenha(senhaCriptografada);
        Cargo cargo = cargoRepository.findByNome("USER").orElseThrow(()-> new RuntimeException("CARGO USER NÃO ENCONTRADO"));
        usuario.setCargo(cargo);

        usuarioRepository.save(usuario);
        return conversaoUsuarioParaResponseDTO(usuario);

    }
    

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosResponseDTO =  new ArrayList<>(); 
        for(Usuario usuario :listaUsuarios){
            UsuarioResponseDTO usuarioResponseDTO = conversaoUsuarioParaResponseDTO(usuario); 
            listaUsuariosResponseDTO.add(usuarioResponseDTO);
        }
        return listaUsuariosResponseDTO;
    }
    
    private UsuarioResponseDTO conversaoUsuarioParaResponseDTO(Usuario usuario){
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getCargo() != null ? usuario.getCargo().getId() : null
        );
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
        return conversaoUsuarioParaResponseDTO(usuario);
        
    }

    public void atualizarUsuario(UsuarioUpdateAdminDTO usuarioUpdateAdminDTO, Long id){
        Usuario auxiliar = usuarioRepository.getReferenceById(id);
        
        if(usuarioUpdateAdminDTO.usuarioNome()!=null) auxiliar.setNome(usuarioUpdateAdminDTO.usuarioNome());
        if(usuarioUpdateAdminDTO.usuarioEmail()!=null){
            if(!usuarioRepository.existsByEmailAndIdNot(usuarioUpdateAdminDTO.usuarioEmail(), id)){
                auxiliar.setEmail(usuarioUpdateAdminDTO.usuarioEmail());
            }
            else{
                throw new RuntimeException("Tentando usar email já cadastrado");
            }
        } 
        if(usuarioUpdateAdminDTO.cargoId() != null){
            Cargo cargo = cargoRepository.findById(usuarioUpdateAdminDTO.cargoId())
            .orElseThrow(()-> new RuntimeException("Não encontrado cargo desejado"));
            auxiliar.setCargo(cargo);
        }

        usuarioRepository.save(auxiliar);
    }


}
