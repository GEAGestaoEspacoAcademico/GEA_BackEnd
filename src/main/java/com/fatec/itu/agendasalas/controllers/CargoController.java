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


@CrossOrigin
@RestController
@RequestMapping("cargos")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<Cargo>> listarTodosCargos(){
        return ResponseEntity.ok(cargoService.listarTodosCargos());
    }
    
    @PostMapping
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo){
        Cargo cargoResponse = cargoService.cadastrarCargo(cargo);
        return ResponseEntity.created(null).body(cargoResponse);
    }
}
