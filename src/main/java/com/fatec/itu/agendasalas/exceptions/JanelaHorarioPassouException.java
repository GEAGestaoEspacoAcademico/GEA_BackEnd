package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.LocalTime;

public class JanelaHorarioPassouException extends RuntimeException{

    public JanelaHorarioPassouException(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        super("Tentativa de agendar na " + data + " em um horário que já passou (" + horaInicio  + "-" + horaFim);
    }

}
