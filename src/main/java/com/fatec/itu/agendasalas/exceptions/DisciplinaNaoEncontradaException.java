package com.fatec.itu.agendasalas.exceptions;

public class DisciplinaNaoEncontradaException extends RuntimeException {

    public DisciplinaNaoEncontradaException(Long id) {
        super("Disciplina de id " + id + " não foi encontrada");
    }
    
}
