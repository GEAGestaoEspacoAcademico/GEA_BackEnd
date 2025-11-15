package com.fatec.itu.agendasalas.exceptions;

public class SalaNaoEncontradaException extends RuntimeException {

    public SalaNaoEncontradaException(Long id) {
    super("Sala de id " + id + " não foi encontrada");
    }
    
}
