package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.UsuarioDTO;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
   
    
    @PostMapping("register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO usuarioDTO){
        
        Usuario user = new Usuario(usuarioDTO.getLogin(), usuarioDTO.getEmail(),usuarioDTO.getNome(), usuarioDTO.getSenha());
        
        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(user);
        UsuarioDTO responseDTO = new UsuarioDTO();
        responseDTO.setId(usuarioSalvo.getId());
        responseDTO.setNome(usuarioSalvo.getNome());
        responseDTO.setEmail(usuarioSalvo.getEmail());
        responseDTO.setLogin(usuarioSalvo.getLogin());
        responseDTO.setCargo(usuarioSalvo.getCargo());
        return ResponseEntity.created(null).body(responseDTO);
    }

    @GetMapping("login")
    public String teste() {
        return "Auth controller funciona!";
    }
}
