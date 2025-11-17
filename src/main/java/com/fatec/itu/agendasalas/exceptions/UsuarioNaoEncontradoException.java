package com.fatec.itu.agendasalas.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(Long id){
        super("Usuario de id " + id + " não foi encontrado");
    }
    
}
