package com.fatec.itu.agendasalas.controllers;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaCreationDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaUpdateDTO;
import com.fatec.itu.agendasalas.services.SecretariaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("secretaria")
@Tag(name = "Secretaria", description="Operações relacionadas a entidade Secretaria")
public class SecretariaController {
      

    @Autowired
    private SecretariaService secretariaService;
    
    @Operation(summary = "Lista todos os funcionários da secretaria")
  
    @GetMapping
    public ResponseEntity<List<SecretariaResponseDTO>> listarSecretarios() {
        return ResponseEntity.ok(secretariaService.listarSecretarios());
    }

    @GetMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> buscarSecretariaPeloId(@PathVariable Long id){
        return ResponseEntity.ok(secretariaService.buscarSecretarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<SecretariaResponseDTO> cadastrarSecretaria(@Valid @RequestBody SecretariaCreationDTO secretariaCreationDTO){
        SecretariaResponseDTO secretariaResponseDTO = secretariaService.cadastrarSecretaria(secretariaCreationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(secretariaResponseDTO.usuarioId()).toUri();

        return ResponseEntity.created(uri).body(secretariaResponseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> atualizarSecretaria(@PathVariable Long id, @Valid @RequestBody SecretariaUpdateDTO secretariaUpdateDTO){
        return ResponseEntity.ok(secretariaService.atualizarSecretaria(id, dto))
    }
}
