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

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.services.DisciplinaService;

@CrossOrigin
@RestController
@RequestMapping("disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public Disciplina criarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaService.criar(disciplina);
    }

    @GetMapping
    public List<Disciplina> listarDisciplinas() {
        return disciplinaService.listar();
    }

    @GetMapping("{id}")
    public Disciplina buscarPorId(@PathVariable Integer id) {
        return disciplinaService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public Disciplina editarDisciplina(@PathVariable Integer id, @RequestBody Disciplina novaDisciplina) {
        return disciplinaService.atualizar(id, novaDisciplina);
    }

    @DeleteMapping("{id}")
    public void excluirDisciplina(@PathVariable Integer id) {
        disciplinaService.excluir(id);
    }
}
