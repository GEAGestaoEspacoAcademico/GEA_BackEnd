package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.repositories.ProfessorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessorService {
    
    private ProfessorRepository professorRepository;

    public List<Professor> listarProfessores(){
        return professorRepository.findAll();
    }

    public void excluirProfessor(Long registroProfessor) {
        if(professorRepository.existsById(registroProfessor)) {
            professorRepository.deleteById(registroProfessor);
        } else {
            throw new EntityNotFoundException("Professor com Registro " + registroProfessor + " não encontrado.");
        }
    }

    

}