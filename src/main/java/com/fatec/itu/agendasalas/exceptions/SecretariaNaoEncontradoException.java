package com.fatec.itu.agendasalas.exceptions;

public class SecretariaNaoEncontradoException extends RuntimeException {

    public SecretariaNaoEncontradoException(Long id) {
        super("O funcionário da secretaria de id: " + id + " não foi localizado");
    }


}
