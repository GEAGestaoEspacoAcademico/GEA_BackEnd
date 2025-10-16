package com.fatec.itu.agendasalas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.repositories.CursoRepository;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public Curso criarCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Curso> buscarPorId(@PathVariable Long id) {
        return cursoRepository.findById(id);
    }

    @PutMapping("{id}")
    public Curso editarCurso(@PathVariable Long id, @RequestBody Curso novoCurso) {
        return cursoRepository.findById(id)
                .map(c -> {
                    c.setNomeCurso(novoCurso.getNomeCurso());
                    c.setCoordenador(novoCurso.getCoordenador());
                    return cursoRepository.save(c);
                })
                .orElseGet(() -> {
                    novoCurso.setId(id);
                    return cursoRepository.save(novoCurso);
                });
    }

    @DeleteMapping("{id}")
    public void excluirCurso(@PathVariable Long id) {
        cursoRepository.deleteById(id);
    }
}
