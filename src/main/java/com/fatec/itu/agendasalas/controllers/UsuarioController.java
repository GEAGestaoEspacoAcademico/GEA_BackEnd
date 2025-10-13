package com.fatec.itu.agendasalas.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping 
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.created(null).body(usuarioService.cadastrarUsuario(usuario));
    } 

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable long id, @RequestBody Map<String, Object> usuario){
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }

}
