package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConflitoAoAgendarException extends RuntimeException{
    
    public ConflitoAoAgendarException(String nomeSala, LocalDate data, LocalTime horaInicio, LocalTime horaFim){
        super("Erro ao agendar na sala " + nomeSala + " no dia " + 
            data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
            " das " + horaInicio.toString()  + " as " + horaFim.toString());
    }
}
