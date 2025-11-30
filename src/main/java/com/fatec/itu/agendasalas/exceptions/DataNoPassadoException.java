package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataNoPassadoException extends RuntimeException {

    public DataNoPassadoException(LocalDate data) {
        super("A data do agendamento não pode ser anterior ao dia atual. A data que apresentou erro foi: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}
