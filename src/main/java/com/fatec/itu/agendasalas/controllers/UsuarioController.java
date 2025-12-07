package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.usersDTO.ResetSenhaResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAlterarSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioFuncionarioDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioRedefinirSenhaByAdDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioRedefinirSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResetSenhaEmailDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.services.PasswordResetEmailService;
import com.fatec.itu.agendasalas.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordResetEmailService passwordResetEmailService;

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
        return ResponseEntity.noContent().build();   
    }

    
    @Operation(summary = "Realiza um pedido de redefinição de senha que será enviada para o e-mail do usuário")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se o seu e-mail existir, enviaremos um link de confirmação",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResetSenhaResponseDTO.class))
    )
    })
    @PostMapping("resetPassword")
    public ResponseEntity<ResetSenhaResponseDTO> resetPassword (
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description="E-mail para receber solicitação de senha",
            required=true,
            content=@Content(mediaType="application/json",
                schema=@Schema(implementation=UsuarioResetSenhaEmailDTO.class)
            )
        )
        @Valid @RequestBody UsuarioResetSenhaEmailDTO dto) throws MessagingException{
    
        return ResponseEntity.ok(passwordResetEmailService.solicitarResetDeSenha(dto.email()));
    }
    
    @Operation(summary = "Lista funcionários (Auxiliar Docente, Professor, Coordenador, Secretaria)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de funcionários encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = UsuarioFuncionarioDTO.class),
                examples = @ExampleObject(value = "[{ \"usuarioId\": 9, \"usuarioNome\": \"Prof. Luis dos Santos\", \"usuarioEmail\": \"luis.santos@fatec.sp.gov.br\", \"registro\": 1014, \"cargoId\": 3, \"cargoNome\": \"PROFESSOR\" }, { \"usuarioId\": 10, \"usuarioNome\": \"Coord. Lucimar de Santi\", \"usuarioEmail\": \"lucimar.desanti@fatec.sp.gov.br\", \"registro\": 2001, \"cargoId\": 4, \"cargoNome\": \"COORDENADOR\" }]")))})

    @GetMapping("funcionarios")
    public ResponseEntity<List<UsuarioFuncionarioDTO>> listarFuncionarios() {
        List<UsuarioFuncionarioDTO> lista = usuarioService.listarFuncionarios();
        return ResponseEntity.ok(lista);
    }
 
    @Operation(summary = "Deleta um usuário desfazendo relações que violam integridade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode="409", description="Erro ao excluir secretaria, pois não há 2 secretários cadastrados pelo menos")
    })
    @DeleteMapping("{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(
        @Parameter(description = "ID do usuário a ser deletado") @PathVariable Long usuarioId){
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{usuarioId}/senha/redefinir")
    @Operation(summary = "Redefinir senha de usuário (administrativo)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redefinirSenhaByAD(
            @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioRedefinirSenhaByAdDTO dto) {
        usuarioService.redefinirSenhaByAD(usuarioId, dto);
    }


    @Operation(summary = "Redefine a senha do usuário usando o token recebido por e-mail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso", content = @Content(mediaType = "application/json",
        schema=@Schema(implementation = ResetSenhaResponseDTO.class))),
          
        @ApiResponse(responseCode = "400", description = "Token inválido", content = @Content(mediaType = "application/json",
        schema=@Schema(implementation = ResetSenhaResponseDTO.class)))
        }
        
    )
    @PatchMapping("alterarSenha")
    public ResponseEntity<ResetSenhaResponseDTO> alterarSenha(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Token de redefinição de senha e nova senha",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioRedefinirSenhaDTO.class)
            )
            )
        @RequestBody UsuarioRedefinirSenhaDTO dto){
        boolean result = passwordResetEmailService.validarPasswordResetToken(dto.token());
        if(!result){
            return ResponseEntity.badRequest().body(new ResetSenhaResponseDTO("Token inválido"));
        }
        usuarioService.redefinirSenha(dto);
        return ResponseEntity.ok(new ResetSenhaResponseDTO("Senha redefinida com sucesso"));
    }
}
