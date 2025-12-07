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
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioRegisterDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.services.AuthService;
import com.fatec.itu.agendasalas.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Usuário registrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ \"usuarioId\": 45, \"usuarioNome\": \"Ana Maria\", \"usuarioEmail\": \"ana.maria@fatec.edu.br\", \"cargoId\": 2 }"))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: email duplicado)")
    })
    @PostMapping("register")
    public ResponseEntity<UsuarioResponseDTO> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação de um novo usuário",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioRegisterDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "{ \"usuarioNome\": \"Felipa Elisélvisky\", \"usuarioEmail\": \"felipa.eliselvisky@exemplo.com\" }")))
           @Valid @RequestBody UsuarioRegisterDTO usuarioRegisterDTO) {
        UsuarioResponseDTO responseDTO = usuarioService.cadastrarUsuarioPeloNomeEmail(usuarioRegisterDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Valida um usuário e retorna um token (Login)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Login bem-sucedido",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioAuthenticationResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ \"usuarioId\": 12, \"usuarioNome\": \"Lucas Silva\", \"usuarioCargo\": \"Professor\" }"))),
        @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    })
    @PostMapping("login")
    public ResponseEntity<UsuarioAuthenticationResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciais do usuário",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioAuthenticationDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "{ \"usuarioLogin\": \"lucas.silva\", \"usuarioSenha\": \"SenhaForte123!\" }")))
            @RequestBody UsuarioAuthenticationDTO usuarioAuthDTO) {
                
             UsuarioAuthenticationResponseDTO authDTO = authService.login(usuarioAuthDTO);
             return ResponseEntity.ok(authDTO);
     }
}
