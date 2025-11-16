package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAuthenticationResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.services.AuthService;
import com.fatec.itu.agendasalas.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Cria um novo usuário")
    @PostMapping("register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioCreationDTO usuarioDTO) {

        UsuarioResponseDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Valida um usuário")
    @PostMapping("login")
    public ResponseEntity<UsuarioAuthenticationResponseDTO> login(
             @RequestBody UsuarioAuthenticationDTO usuarioAuthDTO) {

         try {
             UsuarioAuthenticationResponseDTO authDTO = authService.login(usuarioAuthDTO);
             return ResponseEntity.ok(authDTO);
         } catch (Exception e) {
             throw new RuntimeException(e.getMessage());
         }

     }
}
