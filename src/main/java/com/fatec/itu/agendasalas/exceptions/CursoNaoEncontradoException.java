package com.fatec.itu.agendasalas.exceptions;

public class CursoNaoEncontradoException extends RuntimeException{

    public CursoNaoEncontradoException(String sigla){
        super("Não foi encontrado curso com a sigla: " + sigla);
    }
}
