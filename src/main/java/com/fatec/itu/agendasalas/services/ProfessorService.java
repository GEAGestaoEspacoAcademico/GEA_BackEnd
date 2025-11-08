package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorUpdateDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Disciplina;
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
    public ProfessorResponseDTO buscarPorId(Long id) {
        return toResponseDTO(professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado")));
    }

    public List<ProfessorResponseDTO> listarProfessores() {
        return professorRepository.findAll().stream()
                .map(p -> new ProfessorResponseDTO(p.getId(), p.getNome(), p.getEmail(),
                        p.getRegistroProfessor(),
                        p.getCargo() != null ? p.getCargo().getId() : null))
                .toList();
    }

    public List<CursoListByProfessorDTO> listarCursosPorProfessor(Long idProfessor) {
        List<Curso> cursosEncontrados = cursoRepository.findCursosByProfessorRegistro(idProfessor);
        return cursosEncontrados.stream()
                .map(curso -> new CursoListByProfessorDTO(curso.getId(), curso.getNomeCurso()))
                .toList();
    }

    @Transactional
    public void excluirProfessor(Long registroProfessor) {

        Optional<Professor> professorOptional =
                professorRepository.deleteByRegistroProfessor(registroProfessor);

        if (professorOptional.isPresent()) {
            Professor professorParaDeletar = professorOptional.get();
            professorRepository.deleteById(professorParaDeletar.getId());
        } else {
            throw new EntityNotFoundException(
                    "Professor com Registro " + registroProfessor + " não encontrado.");
        }
    }

    private ProfessorResponseDTO toResponseDTO(Professor p) {
        return new ProfessorResponseDTO(p.getId(), p.getNome(), p.getEmail(),
                p.getRegistroProfessor(), p.getCargo() != null ? p.getCargo().getId() : null);
    }

    @Transactional
    public ProfessorResponseDTO atualizarProfessor(
        ProfessorUpdateDTO dto,
        DisciplinaService disciplinaService,
        CargoService cargoService) {

        Professor professor = professorRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        if (dto.nome() != null) professor.setNome(dto.nome());
        if (dto.email() != null) professor.setEmail(dto.email());

        if (dto.cargoId() != null) {
            Cargo cargo = cargoService.findById(dto.cargoId());
            professor.setCargo(cargo);
        }

        if (dto.disciplinasIds() != null) {

            var disciplinas = dto.disciplinasIds()
                    .stream()
                    .map(disciplinaService::findById)
                    .toList();

            var novasIds = disciplinas.stream()
                    .map(Disciplina::getId)
                    .collect(Collectors.toSet());

            var disciplinasAtuais = professor.getDisciplinas();
            if (disciplinasAtuais != null) {
                disciplinasAtuais.stream()
                        .filter(d -> d.getId() != null && !novasIds.contains(d.getId()))
                        .forEach(d -> d.setProfessor(null));
            }

            disciplinas.forEach(d -> d.setProfessor(professor));

            professor.setDisciplinas(disciplinas);
        }

    return toResponseDTO(professorRepository.save(professor));
}



}
