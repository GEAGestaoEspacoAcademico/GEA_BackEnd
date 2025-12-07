package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaCreationDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaUpdateDTO;
import com.fatec.itu.agendasalas.services.SecretariaService;

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

@RestController
@CrossOrigin
@RequestMapping("secretaria")
@Tag(name = "Secretaria", description="Operações relacionadas a entidade Secretaria")
public class SecretariaController {
      

    @Autowired
    private SecretariaService secretariaService;
    
    @Operation(summary = "Lista todos os funcionários da secretaria")
    @ApiResponse(responseCode="200", description="Listagem de funcionários da secretaria realizada com sucesso",
        content= @Content(mediaType="application/json", schema=@Schema(type="array", implementation=SecretariaResponseDTO.class),
      examples = @ExampleObject(
            value = "[\n" +
                    "  {\n" +
                    "    \"usuarioId\": 1,\n" +
                    "    \"secretarioNome\": \"Maria Silva\",\n" +
                    "    \"secretarioLogin\": \"maria.silva\",\n" +
                    "    \"secretarioEmail\": \"maria.silva@email.com\",\n" +
                    "    \"matricula\": 12345,\n" +
                    "    \"cargoId\": 5\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"usuarioId\": 2,\n" +
                    "    \"secretarioNome\": \"João Souza\",\n" +
                    "    \"secretarioLogin\": \"joão.souza\",\n" +
                    "    \"secretarioEmail\": \"joao.souza@email.com\",\n" +
                    "    \"matricula\": 12346,\n" +
                    "    \"cargoId\": 5\n" +
                    "  }\n" +
                    "]"))
    )
    @GetMapping
    public ResponseEntity<List<SecretariaResponseDTO>> listarSecretarios() {
        return ResponseEntity.ok(secretariaService.listarSecretarios());
    }


    @Operation(summary = "Busca uma secretaria pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Secretaria encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SecretariaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> buscarSecretariaPeloId(@PathVariable Long id){
        return ResponseEntity.ok(secretariaService.buscarSecretarioPorId(id));
    }


    @Operation(summary = "Cadastra uma nova secretaria")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Secretaria cadastrada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SecretariaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<SecretariaResponseDTO> cadastrarSecretaria(
        @Parameter(description = "Dados da secretaria a ser cadastrada", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO com informações da secretaria",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SecretariaCreationDTO.class),
                examples = @ExampleObject(
                    value = "{\n" +
                            "  \"nome\": \"Maria Silva\",\n" +
                            "  \"email\": \"maria.silva@email.com\",\n" +
                            "  \"matricula\": 12345\n" +
                            "}"
                )
            )
        )
        @Valid @RequestBody SecretariaCreationDTO secretariaCreationDTO) throws MessagingException{
        SecretariaResponseDTO secretariaResponseDTO = secretariaService.cadastrarUsuario(secretariaCreationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(secretariaResponseDTO.usuarioId()).toUri();

        return ResponseEntity.created(uri).body(secretariaResponseDTO);
    }


     @Operation(summary = "Atualiza uma secretaria existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Secretaria atualizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SecretariaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Secretaria não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> atualizarSecretaria(
         @PathVariable Long id,
        @Parameter(description = "Campos a serem atualizados na secretaria", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO com campos opcionais para atualização",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SecretariaUpdateDTO.class),
                examples = @ExampleObject(
                    value = "{\n" +
                            "  \"nome\": \"Maria Souza\",\n" +
                            "  \"email\": \"maria.souza@email.com\"\n" +
                            "}"
                )
            )
        )
        @Valid @RequestBody SecretariaUpdateDTO secretariaUpdateDTO){
        return ResponseEntity.ok(secretariaService.atualizarSecretaria(id, secretariaUpdateDTO));
    }


    /* 
    @Operation(summary = "Deleta uma secretaria pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Secretaria deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarSecretaria(@PathVariable Long id){
        secretariaService.deletarSecretaria(id);
        return ResponseEntity.noContent().build();
    }
    */
}
