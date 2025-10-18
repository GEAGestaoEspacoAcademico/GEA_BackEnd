package com.fatec.itu.agendasalas.controllers;

import com.fatec.itu.agendasalas.dto.CoordenadorCreationDTO;
import com.fatec.itu.agendasalas.dto.CoordenadorResponseDTO;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.exceptions.CoordenadorNaoEncontradoException;
import com.fatec.itu.agendasalas.exceptions.RegistroCoordenacaoDuplicadoException;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.services.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/coordenadores")
@Validated
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> promover(@RequestBody @Validated CoordenadorCreationDTO dto) {
        if (coordenadorRepository.existsByRegistroCoordenacao(dto.getRegistroCoordenacao())) {
            throw new RegistroCoordenacaoDuplicadoException("Registro de coordenação já existe");
        }
        Coordenador coordenador = coordenadorService.promoverParaCoordenador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(coordenador));
    }

    
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public List<CoordenadorResponseDTO> listar() {
        return coordenadorService.listarCoordenadores().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    
    @GetMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(@PathVariable Long id) {
        Coordenador coordenador = coordenadorService.buscarPorId(id)
                .orElseThrow(() -> new CoordenadorNaoEncontradoException("Coordenador não encontrado"));
        return ResponseEntity.ok(toResponseDTO(coordenador));
    }

    
    @GetMapping("/registro/{registro}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorRegistro(@PathVariable int registro) {
        Coordenador coordenador = coordenadorRepository.findByRegistroCoordenacao(registro)
                .orElseThrow(() -> new CoordenadorNaoEncontradoException("Coordenador não encontrado"));
        return ResponseEntity.ok(toResponseDTO(coordenador));
    }

    
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> despromover(@PathVariable Long id) {
        coordenadorService.despromoverCoordenador(id);
        return ResponseEntity.noContent().build();
    }

    
    private CoordenadorResponseDTO toResponseDTO(Coordenador c) {
        return new CoordenadorResponseDTO(
                c.getId(),
                c.getNome(),
                c.getEmail(),
                c.getRegistroCoordenacao(),
                c.getCargo() != null ? c.getCargo().getId() : null
        );
    }
}
