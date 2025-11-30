package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JanelaHorarioPassouException extends RuntimeException{

    public JanelaHorarioPassouException(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        super("Tentativa de agendar na " 
        + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
        + " em um horário que já passou (" 
        + horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"))
        + "-" 
        + horaFim.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

}
