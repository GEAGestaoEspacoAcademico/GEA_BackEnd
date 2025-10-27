package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.repositories.CursoRepository;
import com.fatec.itu.agendasalas.repositories.ProfessorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    
    /********* Lista por ID *********/
	public Optional<Professor> buscarPorId(Long id) {
		return professorRepository.findById(id);
	}

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public List<CursoListByProfessorDTO> listarCursosPorProfessor(Long idProfessor) {
        List<Curso> cursosEncontrados = cursoRepository.findCursosByProfessorRegistro(idProfessor);
        return cursosEncontrados.stream().map(curso -> new CursoListByProfessorDTO(curso.getId(), curso.getNomeCurso()))
                .toList();
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
