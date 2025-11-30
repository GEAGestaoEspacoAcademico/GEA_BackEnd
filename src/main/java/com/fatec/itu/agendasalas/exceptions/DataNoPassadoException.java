package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;

public class DataNoPassadoException extends RuntimeException {

    public DataNoPassadoException(String nome, LocalDate data) {
        super("O usuário: " + nome + " tentou realizar um agendamento em uma data que já passou: " + data);
    }

}
