package com.fatec.itu.agendasalas.exceptions.janelasHorario;

public class SalaIdObrigatorioException extends RuntimeException {

    public SalaIdObrigatorioException() {
        super("Campo salaId é obrigatório");
    }

}
