package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.services.CargoService;

@CrossOrigin
@RestController
@RequestMapping("cargos")
public class CargoController {
    
    private final CargoService cargoService;

    public CargoController(CargoService cargoService){
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> listarTodosCargos(){

        return ResponseEntity.ok(cargoService.listarTodosCargos());
    }
    
}
