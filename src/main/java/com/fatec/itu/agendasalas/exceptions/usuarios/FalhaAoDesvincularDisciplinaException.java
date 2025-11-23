package com.fatec.itu.agendasalas.exceptions.usuarios;

public class FalhaAoDesvincularDisciplinaException extends RuntimeException{
    public FalhaAoDesvincularDisciplinaException(Long usuarioId, Throwable cause){
        super("Falha ao desvincular disciplinas do professor id=" + usuarioId, cause);
    }
}
