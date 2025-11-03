package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.dto.paginacaoDTO.PageableResponseDTO;
import com.fatec.itu.agendasalas.services.AuxiliarDocenteService;

@RestController
public class AuxiliarDocenteController {

    @Autowired
    private AuxiliarDocenteService auxiliarDocenteService;

    @GetMapping
    public ResponseEntity<PageableResponseDTO<AuxiliarDocenteResponseDTO>> listarAuxiliaresDocentes(@RequestParam (defaultValue="0") int size, @RequestParam(defaultValue="10") int page){
        Page<AuxiliarDocenteResponseDTO> paginacaoAuxiliarDocentes = auxiliarDocenteService.listarAuxiliaresDocentes(page, size);

        return ResponseEntity.ok(PageableResponseDTO.fromPage(paginacaoAuxiliarDocentes));

    }

}
