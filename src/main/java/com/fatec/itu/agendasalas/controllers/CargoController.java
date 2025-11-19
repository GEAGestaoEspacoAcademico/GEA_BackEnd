package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.services.CargoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin
@RestController
@RequestMapping("cargos")
@Tag(name = "Cargo", description = "Operações relacionadas a cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping("professor")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<String> professor(){
        return ResponseEntity.ok("oi, professor");
    }

    @GetMapping("coordenador")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<String> coordenador(){
        return ResponseEntity.ok("oi, coordenador, que também pode ser um professor!");
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> usuario(){
        return ResponseEntity.ok("oi, usuario");
    }

    @GetMapping("auxiliar-docente")
    @PreAuthorize("hasRole('AUXILIAR_DOCENTE')")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("oi, auxiliar docente");
    }


    @Operation(summary = "Lista todos os cargos existentes")
    @GetMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<List<Cargo>> listarTodosCargos() {
        
        return ResponseEntity.ok(cargoService.listarTodosCargos());
    }

    @Operation(summary = "Cria um novo cargo")
    @PostMapping
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo) {
        Cargo cargoResponse = cargoService.cadastrarCargo(cargo);
        return ResponseEntity.created(null).body(cargoResponse);
    }
}
