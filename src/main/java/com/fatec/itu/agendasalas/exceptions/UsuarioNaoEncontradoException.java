package com.fatec.itu.agendasalas.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(Long id){
        super("Usuario de id " + id + " não foi encontrado");
    }
    
    public UsuarioNaoEncontradoException(String email){
        super("Usuario de email " + email + " não foi encontrado");
    }
}
