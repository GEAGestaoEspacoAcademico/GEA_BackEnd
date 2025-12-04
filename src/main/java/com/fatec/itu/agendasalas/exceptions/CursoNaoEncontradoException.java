package com.fatec.itu.agendasalas.exceptions;

public class CursoNaoEncontradoException extends RuntimeException{

    public CursoNaoEncontradoException(Long id){
        super("Não foi encontrado curso com o id: " + id);
    }
    
    public CursoNaoEncontradoException(String sigla){
        super("Não foi encontrado curso com a sigla: " + sigla);
    }
}
