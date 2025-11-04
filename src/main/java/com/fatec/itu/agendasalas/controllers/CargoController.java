package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
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

    @GetMapping("user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("oi, user");
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("oi, admin");
    }


    @Operation(summary = "Lista todos os cargos existentes")
    @GetMapping
    @PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
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
