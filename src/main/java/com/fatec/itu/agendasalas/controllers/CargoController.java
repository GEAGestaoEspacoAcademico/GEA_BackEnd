package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.services.CargoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin
@RestController
@RequestMapping("cargos")
@Tag(name = "Cargo", description = "Operações relacionadas a cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @Operation(summary = "Endpoint de teste para usuário com autoridade 'USER'")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem de teste para USER")
    })
    @GetMapping("user")
    //@PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("oi, user");
    }


    @Operation(summary = "Endpoint de teste para usuário com autoridade 'AUXILIAR_DOCENTE'")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem de teste para AUXILIAR_DOCENTE")
    })  
    @GetMapping("admin")
   // @PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("oi, admin");
    }

    @Operation(summary = "Lista todos os cargos existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de cargos encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", implementation = Cargo.class)))
    })
    @GetMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<List<Cargo>> listarTodosCargos() {
        
        return ResponseEntity.ok(cargoService.listarTodosCargos());
    }

    @Operation(summary = "Cria um novo cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Cargo criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: nome duplicado)")
    })
    @PostMapping
    public ResponseEntity<Cargo> cadastrarCargo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de um novo cargo",
                    required = true,
                    content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Cargo.class)))
            @RequestBody Cargo cargo) {
        Cargo cargoResponse = cargoService.cadastrarCargo(cargo);
        return ResponseEntity.created(null).body(cargoResponse);
    }
}
