package com.fatec.itu.agendasalas.exceptions;

public class RecursoJaAdicionadoNaSalaException extends RuntimeException{

    public RecursoJaAdicionadoNaSalaException(Long recursoId, Long salaId) {
        super("Não foi possível adicionar o recurso " + recursoId + " na sala " + salaId + " pois já estava adicionado");
    }



}
