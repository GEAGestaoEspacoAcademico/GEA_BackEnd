package com.fatec.itu.agendasalas.exceptions;

public class JWTNaoEValidoException extends RuntimeException{

    public JWTNaoEValidoException(String message) {
        super(message);
    }
}
