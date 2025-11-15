package com.fatec.itu.agendasalas.exceptions;

public class JanelasHorarioNaoEncontradaException extends RuntimeException{

    public JanelasHorarioNaoEncontradaException(Long id) {
        super("A janela de horário com id " + id + " não foi encontrada");
    }


}
