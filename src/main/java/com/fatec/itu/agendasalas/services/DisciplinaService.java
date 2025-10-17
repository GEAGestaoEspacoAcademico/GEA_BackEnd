package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Disciplina criar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(Integer id) {
        return disciplinaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }

    public Disciplina atualizar(Integer id, Disciplina novaDisciplina) {
        Disciplina atual = buscarPorId(id);
        atual.setNome(novaDisciplina.getNome());
        atual.setSemestre(novaDisciplina.getSemestre());
        atual.setProfessor(novaDisciplina.getProfessor());
        atual.setCurso(novaDisciplina.getCurso());
        return disciplinaRepository.save(atual);
    }

    public void excluir(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada");
        }
        disciplinaRepository.deleteById(id);
    }
}