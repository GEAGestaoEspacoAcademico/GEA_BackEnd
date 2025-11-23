package com.fatec.itu.agendasalas.exceptions.usuarios;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(Long id){
        super("Usuario de id " + id + " não foi encontrado");
    }

}
