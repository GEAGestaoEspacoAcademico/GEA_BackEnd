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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAlterarSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = UsuarioResponseDTO.class),
                examples = @ExampleObject(value = "[ { \"usuarioId\": 12, \"usuarioNome\": \"Lucas Silva\", \"usuarioEmail\": \"lucas.silva@fatec.edu.br\", \"cargoId\": 3 } ]")))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> responseDTOList = usuarioService.listarUsuarios();
        return ResponseEntity.ok(responseDTOList);
    }

    @Operation(summary = "Busca um usuário específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class),
                examples = @ExampleObject(value = "{ \"usuarioId\": 12, \"usuarioNome\": \"Lucas Silva\", \"usuarioEmail\": \"lucas.silva@fatec.edu.br\", \"cargoId\": 3 }"))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(
        @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        UsuarioResponseDTO responseDTO = usuarioService.buscarUsuarioPorId(usuarioId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Atualiza dados administrativos de um usuário (Admin)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("{usuarioId}")
    public ResponseEntity<Void> atualizarUsuarioAdmin(
        @Parameter(description = "ID do usuário a ser atualizado") @PathVariable Long usuarioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para atualização administrativa",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioUpdateAdminDTO.class),
                    examples = @ExampleObject(value = "{ \"usuarioNome\": \"Lucas Silva\", \"usuarioEmail\": \"lucas.silva@fatec.edu.br\", \"cargoId\": 3 }")))
            @RequestBody UsuarioUpdateAdminDTO usuarioUpdateAdminDTO) {
        usuarioService.atualizarUsuario(usuarioUpdateAdminDTO, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Altera a senha do usuário quando o mesmo está logado")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Senha alterada") })
    @PatchMapping("{usuarioId}/senha")
    public ResponseEntity<Void> alterarSenha(
        @Parameter(description = "ID do usuário") @PathVariable Long usuarioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para troca de senha",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioAlterarSenhaDTO.class),
                    examples = @ExampleObject(value = "{ \"senhaAtual\": \"SenhaAntiga123!\", \"novaSenha\": \"NovaSenhaSegura2025!\", \"repetirNovaSenha\": \"NovaSenhaSegura2025!\" }")))
            @RequestBody UsuarioAlterarSenhaDTO dto) {
        usuarioService.alterarSenha(usuarioId, dto);
        return ResponseEntity.noContent().build();    }

    @Operation(summary = "Deleta um usuário desfazendo relações que violam integridade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(
        @Parameter(description = "ID do usuário a ser deletado") @PathVariable Long usuarioId){
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
