package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.repositories.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso criar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado. Id=" + id));
    }

    public Curso atualizar(Long id, Curso novoCurso) {
        Curso atual = buscarPorId(id);
        atual.setNomeCurso(atual.getNomeCurso());
        atual.setCoordenador(atual.getCoordenador());
        return cursoRepository.save(atual);
    }

    public void excluir(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado. Id=" + id);
        }
        cursoRepository.deleteById(id);
    }
}