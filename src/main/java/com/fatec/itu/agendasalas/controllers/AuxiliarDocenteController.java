package com.fatec.itu.agendasalas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteCreationDTO;
import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.dto.paginacaoDTO.PageableResponseDTO;
import com.fatec.itu.agendasalas.services.AuxiliarDocenteService;



@RestController
@CrossOrigin
@RequestMapping("auxiliar-docentes")
public class AuxiliarDocenteController {

    @Autowired
    private AuxiliarDocenteService auxiliarDocenteService;

    @GetMapping
    @PreAuthorize("hasRole('AUXILIAR_DOCENTE')")
    public ResponseEntity<PageableResponseDTO<AuxiliarDocenteResponseDTO>> listarAuxiliaresDocentes(@RequestParam (defaultValue="0") int page, @RequestParam(defaultValue="10") int size){
        Page<AuxiliarDocenteResponseDTO> paginacaoAuxiliarDocentes = auxiliarDocenteService.listarAuxiliaresDocentes(page, size);

        return ResponseEntity.ok(PageableResponseDTO.fromPage(paginacaoAuxiliarDocentes));

    }
    
    @PostMapping
    //@PreAuthorize("hasAuthority('AUXILIAR_DOCENTE')")
    public ResponseEntity<AuxiliarDocenteResponseDTO> cadastrarAuxiliarDocente(@RequestBody AuxiliarDocenteCreationDTO auxiliarDocenteCreationDTO){
        return ResponseEntity.created(null).body(auxiliarDocenteService.cadastrarUsuario(auxiliarDocenteCreationDTO));

    } 

}
