package com.fatec.itu.agendasalas.exceptions;

public class JWTCreationException extends RuntimeException{

    public JWTCreationException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
