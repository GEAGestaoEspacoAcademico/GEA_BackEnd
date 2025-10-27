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

import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaCreateDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.services.DisciplinaService;

@CrossOrigin
@RestController
@RequestMapping("disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public DisciplinaListDTO criarDisciplina(@RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.criar(novaDisciplina);
    }

    @GetMapping
    public List<DisciplinaListDTO> listarDisciplinas() {
        return disciplinaService.listar();
    }

    @GetMapping("professor/{idProfessor}")
    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(@PathVariable Long idProfessor) {
        return disciplinaService.listarDisciplinasPorProfessor(idProfessor);
    }

    @GetMapping("{idDisciplina}")
    public DisciplinaListDTO buscarPorId(@PathVariable Long idDisciplina) {
        return disciplinaService.buscarPorId(idDisciplina);
    }

    @PutMapping("{idDisciplina}")
    public DisciplinaListDTO editarDisciplina(@PathVariable Long idDisciplina, @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.atualizar(idDisciplina, novaDisciplina);
    }

    @DeleteMapping("{idDisciplina}")
    public void excluirDisciplina(@PathVariable Long idDisciplina) {
        disciplinaService.excluir(idDisciplina);
    }
}
