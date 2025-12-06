package com.fatec.itu.agendasalas.exceptions;

public class RecursoJaExisteException extends RuntimeException{
     public RecursoJaExisteException(String mensagem) {
        super(mensagem);
    }
}
