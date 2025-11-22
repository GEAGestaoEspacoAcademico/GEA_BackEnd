package com.fatec.itu.agendasalas.exceptions;

public class MatriculaDuplicadaSecretariaException extends RuntimeException{

    public MatriculaDuplicadaSecretariaException(long matricula) {
        super("Tentativa de criar ou atualizar um secretario com matricula ja existente: " + matricula);
    }



}
