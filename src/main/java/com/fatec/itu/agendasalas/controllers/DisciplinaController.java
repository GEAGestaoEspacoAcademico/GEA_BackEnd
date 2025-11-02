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
import com.fatec.itu.agendasalas.services.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("disciplinas")
@Tag(name = "Disciplina", description = "Operações relacionadas a disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Operation(summary = "Cria uma nova disciplina")
    @PostMapping
    public DisciplinaListDTO criarDisciplina(@RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.criar(novaDisciplina);
    }

    @Operation(summary = "Lista todas as disciplinas existentes")
    @GetMapping
    public List<DisciplinaListDTO> listarDisciplinas() {
        return disciplinaService.listar();
    }

    @Operation(summary = "Apresenta uma disciplina existente por id")
    @GetMapping("{disciplinaId}")
    public DisciplinaListDTO buscarPorId(@PathVariable Long disciplinaId) {
        return disciplinaService.buscarPorId(disciplinaId);
    }

    @Operation(summary = "Atualiza uma disciplina pelo id")
    @PutMapping("{disciplinaId}")
    public DisciplinaListDTO editarDisciplina(@PathVariable Long disciplinaId,
            @RequestBody DisciplinaCreateDTO novaDisciplina) {
        return disciplinaService.atualizar(disciplinaId, novaDisciplina);
    }

    @Operation(summary = "Deleta uma disciplina pelo id")
    @DeleteMapping("{disciplinaId}")
    public void excluirDisciplina(@PathVariable Long disciplinaId) {
        disciplinaService.excluir(disciplinaId);
    }
}
