package com.fatec.itu.agendasalas.exceptions;

public class NaoEPossivelExcluirSecretarioException extends RuntimeException{

    public NaoEPossivelExcluirSecretarioException(long totalSecretarios) {
        super("Não foi possível excluir um usuário secretário, pois o total de secretários é menor ou igual a 2. Total secretários: " + totalSecretarios);
    }


}
