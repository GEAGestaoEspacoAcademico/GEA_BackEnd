package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaCreateDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.repositories.CursoRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DisciplinaListDTO criar(DisciplinaCreateDTO disciplina) {
        Curso cursoDisciplina = cursoRepository.findById(disciplina.idCurso()).orElseThrow();

        Disciplina novaDisciplina = disciplinaRepository
                .save(new Disciplina(disciplina.nome(), disciplina.semestre(), cursoDisciplina));

        return new DisciplinaListDTO(novaDisciplina.getId(), novaDisciplina.getNome(), novaDisciplina.getSemestre(),
                novaDisciplina.getCurso().getNomeCurso());
    }

    private List<DisciplinaListDTO> converterParaDTO(List<Disciplina> disciplinas) {
        return disciplinas.stream().map(disciplina -> new DisciplinaListDTO(disciplina.getId(), disciplina.getNome(),
                disciplina.getSemestre(), disciplina.getCurso().getNomeCurso())).toList();
    }

    public List<DisciplinaListDTO> listar() {
        return converterParaDTO(disciplinaRepository.findAll());
    }

    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(Long idProfessor) {
        return converterParaDTO(disciplinaRepository.findByProfessorId(idProfessor));
    }

    public DisciplinaListDTO buscarPorId(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        return new DisciplinaListDTO(disciplina.getId(), disciplina.getNome(),
                disciplina.getSemestre(), disciplina.getCurso().getNomeCurso());
    }

    public DisciplinaListDTO atualizar(Long id, DisciplinaCreateDTO novaDisciplina) {
        Disciplina atual = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        atual.setNome(novaDisciplina.nome());
        atual.setSemestre(novaDisciplina.semestre());
        atual.setCurso(cursoRepository.findById(novaDisciplina.idCurso()).orElseThrow());

        Disciplina disciplinaAtualizada = disciplinaRepository.save(atual);

        return new DisciplinaListDTO(disciplinaAtualizada.getId(), disciplinaAtualizada.getNome(),
                disciplinaAtualizada.getSemestre(), disciplinaAtualizada.getCurso().getNomeCurso());
    }

    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada");
        }
        disciplinaRepository.deleteById(id);
    }
}