package com.fatec.itu.agendasalas.exceptions.usuarios;

public class FalhaAoDesvincularCursoException extends RuntimeException{
    public FalhaAoDesvincularCursoException(Long usuarioId, Throwable cause){
        super("Falha ao desvincular cursos do coordenador id=" + usuarioId, cause);
    }
}
