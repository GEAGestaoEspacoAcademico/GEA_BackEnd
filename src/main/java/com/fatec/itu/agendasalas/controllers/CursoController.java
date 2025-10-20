package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.services.CursoService;

@CrossOrigin
@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public Curso criarCurso(@RequestBody Curso curso) {
        return cursoService.criar(curso);
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.listar();
    }

    @GetMapping("{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public Curso editarCurso(@PathVariable Long id, @RequestBody Curso novoCurso) {
        return cursoService.atualizar(id, novoCurso);
    }

    @DeleteMapping("{id}")
    public void excluirCurso(@PathVariable Long id) {
        cursoService.excluir(id);
    }
}
