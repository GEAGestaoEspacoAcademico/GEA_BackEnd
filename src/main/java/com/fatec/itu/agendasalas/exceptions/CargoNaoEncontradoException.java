package com.fatec.itu.agendasalas.exceptions;

public class CargoNaoEncontradoException extends RuntimeException{

    public CargoNaoEncontradoException(String nome) {
        super("O cargo de nome: " + nome + " não foi encontrado");
    }

    public CargoNaoEncontradoException(Long id) {
        super("O cargo de id: " + id + " não foi encontrado");
    }

}
