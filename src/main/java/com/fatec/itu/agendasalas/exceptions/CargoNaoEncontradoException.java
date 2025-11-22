package com.fatec.itu.agendasalas.exceptions;

public class CargoNaoEncontradoException extends RuntimeException{

    public CargoNaoEncontradoException(String cargo) {
        super("O cargo " + cargo + " não foi encontrado");
    }


}