package com.fatec.itu.agendasalas.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.dto.CoordenadorResponseDTO;
import com.fatec.itu.agendasalas.dto.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.services.ProfessorService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    /********* Lista todos os professores *********/
    @GetMapping("listar")
    public List<ProfessorResponseDTO> listar() {
        return professorService.listarProfessores().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /********* Lista por ID *********/
    @GetMapping("listar/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long id) {
        Professor professor = professorService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));
        return ResponseEntity.ok(toResponseDTO(professor));
    }

    /********* Apaga o professor *********/
    @DeleteMapping("{registroProfessor}")
    public ResponseEntity<Void> delete(@PathVariable long registroProfessor) {
        professorService.excluirProfessor(registroProfessor);
        return ResponseEntity.noContent().build();
    }

    private ProfessorResponseDTO toResponseDTO(Professor p) {
        return new ProfessorResponseDTO(
            p.getId(),
            p.getNome(),
            p.getEmail(),
            p.getRegistroProfessor(),
            p.getCargo() != null ? p.getCargo().getId() : null
        );        
    }
}
