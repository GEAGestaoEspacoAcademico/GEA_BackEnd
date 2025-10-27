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

import com.fatec.itu.agendasalas.dto.cursos.CursoCreateDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoListDTO;
import com.fatec.itu.agendasalas.services.CursoService;

@CrossOrigin
@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public CursoListDTO criarCurso(@RequestBody CursoCreateDTO curso) {
        return cursoService.criar(curso);
    }

    @GetMapping
    public List<CursoListDTO> listarCursos() {
        return cursoService.listar();
    }

    @GetMapping("{idCurso}")
    public CursoListDTO buscarPorId(@PathVariable Long idCurso) {
        return cursoService.buscarPorId(idCurso);
    }

    @GetMapping("/professor/{idProfessor}")
    public List<CursoListDTO> listarPorProfessor(@PathVariable Long idProfessor) {
        return cursoService.listarCursosPorProfessor(idProfessor);
    }

    @PutMapping("{idCurso}")
    public CursoListDTO editarCurso(@PathVariable Long idCurso, @RequestBody CursoCreateDTO novoCurso) {
        return cursoService.atualizar(idCurso, novoCurso);
    }

    @DeleteMapping("{idCurso}")
    public void excluirCurso(@PathVariable Long idCurso) {
        cursoService.excluir(idCurso);
    }
}
