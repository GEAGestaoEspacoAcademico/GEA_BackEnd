package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.auxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.services.AuxiliarDocenteService;

@RestController
public class AuxiliarDocenteController {

    @Autowired
    private AuxiliarDocenteService auxiliarDocenteService;

    @GetMapping
    public ResponseEntity<auxiliarDocenteResponseDTO> listarAuxiliaresDocentes(){
        
    }

}
