package com.fatec.itu.agendasalas.exceptions;

public class ProfessorNotFoundException extends RuntimeException{

    public ProfessorNotFoundException(Long id) {
        super("O professor de id: " + id + " não foi encontrado");
    }
    
}
