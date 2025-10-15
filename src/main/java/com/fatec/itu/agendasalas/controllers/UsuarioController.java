package com.fatec.itu.agendasalas.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.UsuarioDTO;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTO> responseDTOList = new ArrayList<>();
        usuarios.forEach(usuario -> {
            UsuarioDTO responseDTO = new UsuarioDTO();
            responseDTO.setId(usuario.getId());
            responseDTO.setNome(usuario.getNome());
            responseDTO.setEmail(usuario.getEmail());
            responseDTO.setLogin(usuario.getLogin());
            responseDTO.setCargoId(usuario.getCargo().getId());  
            responseDTOList.add(responseDTO);
        });

        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping({"id"})
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable long id){
        Usuario user = usuarioService.buscarUsuarioPorId(id); 
        UsuarioDTO responseDTO = new UsuarioDTO();
        responseDTO.setId(user.getId());
        responseDTO.setNome(user.getNome());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setLogin(user.getLogin());
        responseDTO.setCargoId(user.getCargo().getId());
        return ResponseEntity.ok(responseDTO);
    } 



    @PatchMapping("{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable long id, @RequestBody Map<String, Object> usuario){
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }

}
