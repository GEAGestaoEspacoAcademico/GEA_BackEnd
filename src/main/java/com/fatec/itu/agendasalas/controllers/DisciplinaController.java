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

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;

@RestController
@RequestMapping("disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @PostMapping
    public Disciplina criarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    @GetMapping
    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Disciplina> buscarPorId(@PathVariable Integer id) {
        return disciplinaRepository.findById(id);
    }

    @PutMapping("{id}")
    public Disciplina editarDisciplina(@PathVariable Integer id, @RequestBody Disciplina novaDisciplina) {
        return disciplinaRepository.findById(id)
                .map(d -> {
                    d.setNome(novaDisciplina.getNome());
                    d.setSemestre(novaDisciplina.getSemestre());
                    d.setProfessor(novaDisciplina.getProfessor());
                    d.setCurso(novaDisciplina.getCurso());
                    return disciplinaRepository.save(d);
                })
                .orElseGet(() -> {
                    novaDisciplina.setId(id);
                    return disciplinaRepository.save(novaDisciplina);
                });
    }

    @DeleteMapping("{id}")
    public void excluirDisciplina(@PathVariable Integer id) {
        disciplinaRepository.deleteById(id);
    }
}
