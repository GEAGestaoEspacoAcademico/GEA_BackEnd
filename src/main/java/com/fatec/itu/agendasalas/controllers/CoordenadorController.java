package com.fatec.itu.agendasalas.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorCreationDTO;
import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorResponseDTO;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.exceptions.CoordenadorNaoEncontradoException;
import com.fatec.itu.agendasalas.exceptions.RegistroCoordenacaoDuplicadoException;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.services.CoordenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/coordenadores")
@Tag(name = "Coordenador", description = "Operações relacionadas a coordenador")
@Validated
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Operation(summary = "Cria um novo coordenador")
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> promover(
            @RequestBody @Validated CoordenadorCreationDTO dto) {
        if (coordenadorRepository.existsByRegistroCoordenacao(dto.registroCoordenacao())) {
            throw new RegistroCoordenacaoDuplicadoException("Registro de coordenação já existe");
        }
        Coordenador coordenador = coordenadorService.promoverParaCoordenador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(coordenador));
    }


    @Operation(summary = "Lista todos os coordenadores existentes")
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public List<CoordenadorResponseDTO> listar() {
        return coordenadorService.listarCoordenadores().stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Operation(summary = "Apresenta um coordenador pelo seu id")
    @GetMapping("/{coordenadorId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(@PathVariable Long coordenadorId) {
        Coordenador coordenador = coordenadorService.buscarPorId(
                coordenadorId).orElseThrow(
                () -> new CoordenadorNaoEncontradoException("Coordenador não encontrado"));
        return ResponseEntity.ok(toResponseDTO(coordenador));
    }


    @Operation(summary = "Apresenta um coordenador pelo seu registro")
    @GetMapping("/registro/{registro}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorRegistro(@PathVariable int registro) {
        Coordenador coordenador =
                coordenadorRepository.findByRegistroCoordenacao(registro).orElseThrow(
                        () -> new CoordenadorNaoEncontradoException("Coordenador não encontrado"));
        return ResponseEntity.ok(toResponseDTO(coordenador));
    }


    @Operation(summary = "Deleta um coordenador pelo seu id")
    @DeleteMapping("/{coordenadorId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> despromover(@PathVariable Long coordenadorId) {
        coordenadorService.despromoverCoordenador(coordenadorId);
        return ResponseEntity.noContent().build();
    }


    private CoordenadorResponseDTO toResponseDTO(Coordenador c) {
        return new CoordenadorResponseDTO(c.getId(), c.getNome(), c.getEmail(),
                c.getRegistroCoordenacao(), c.getCargo() != null ? c.getCargo().getId() : null);
    }
}
