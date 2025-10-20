package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> responseDTOList = usuarioService.listarUsuarios();
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id){
        UsuarioResponseDTO responseDTO = usuarioService.buscarUsuarioPorId(id); 
        return ResponseEntity.ok(responseDTO);
    } 

    @PatchMapping("{id}")
    public ResponseEntity<Void> atualizarUsuarioAdmin(@PathVariable Long id, @RequestBody UsuarioUpdateAdminDTO usuarioUpdateAdminDTO){
        usuarioService.atualizarUsuario(usuarioUpdateAdminDTO, id);
        return ResponseEntity.noContent().build();
    }

}
