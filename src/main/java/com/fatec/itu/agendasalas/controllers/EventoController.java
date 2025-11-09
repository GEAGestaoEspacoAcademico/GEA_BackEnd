package com.fatec.itu.agendasalas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.entity.Professor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("eventos")
public class EventoController {

    // Adicionei o serviço de Evento mas ele ainda nao foi criado no projeto;
    @Autowired
    private EventoService eventoService;

    @PostMapping
    public Evento criarEvento(@RequestBody Evento evento) {
        return eventoService.criar(evento);
    }

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.listar();
    }

    @GetMapping("evento/{id}")
    public List<Evento> listarEventosPorSala(@PathVariable Long id, Evento evento) {
        return eventoService.listarEventosPorSala(id);
    }

    @GetMapping("{id}")
    public Evento buscarPorId(@PathVariable Integer id) {
        return eventoService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public Evento editarEvento(@PathVariable Integer id, @RequestBody Evento novoEvento) {
        return eventoService.atualizar(id, novoEvento);
    }

    @DeleteMapping("{id}")
    public void excluirEvento(@PathVariable Integer id) {
        eventoService.excluir(id);
    }
}
