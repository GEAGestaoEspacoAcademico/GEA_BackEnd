package com.fatec.itu.agendasalas.exceptions;

public class ProfessorNaoEncontradoException extends RuntimeException{

    public ProfessorNaoEncontradoException(Long id) {
        super("O professor de id: " + id + " não foi encontrado");
    }
    
}
