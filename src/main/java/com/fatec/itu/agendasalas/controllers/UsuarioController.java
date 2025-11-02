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
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> responseDTOList = usuarioService.listarUsuarios();
        return ResponseEntity.ok(responseDTOList);
    }

    @Operation(summary = "Apresenta um usuário por id")
    @GetMapping("{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long usuarioId) {
        UsuarioResponseDTO responseDTO = usuarioService.buscarUsuarioPorId(usuarioId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Atualiza um usuário existente por id")
    @PatchMapping("{usuarioId}")
    public ResponseEntity<Void> atualizarUsuarioAdmin(@PathVariable Long usuarioId,
            @RequestBody UsuarioUpdateAdminDTO usuarioUpdateAdminDTO) {
        usuarioService.atualizarUsuario(usuarioUpdateAdminDTO, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
