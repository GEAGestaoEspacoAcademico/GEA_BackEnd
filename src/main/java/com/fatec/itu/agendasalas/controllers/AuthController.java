package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
   
    
    @PostMapping("register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioCreationDTO usuarioDTO){

        UsuarioResponseDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.created(null).body(responseDTO);
    }

    @PostMapping("login")
    public String teste() {
        return "Auth controller funciona!";
    }
}
