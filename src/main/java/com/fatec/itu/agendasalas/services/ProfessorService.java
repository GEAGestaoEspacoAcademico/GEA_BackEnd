package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.repositories.ProfessorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService {
    
    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> listarProfessores(){
        return professorRepository.findAll();
    }

    @Transactional
    public void excluirProfessor(Long registroProfessor) {

        Optional<Professor> professorOptional = professorRepository.deleteByRegistroProfessor(registroProfessor);

        if (professorOptional.isPresent()) {
            Professor professorParaDeletar = professorOptional.get();
            professorRepository.deleteById(professorParaDeletar.getId()); 
        } else {
            throw new EntityNotFoundException("Professor com Registro " + registroProfessor + " não encontrado.");
        }
    }
}