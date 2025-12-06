package com.fatec.itu.agendasalas.exceptions;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProfessorJaPossuiAgendamentoEmOutraSalaException extends RuntimeException{


    public ProfessorJaPossuiAgendamentoEmOutraSalaException(LocalDate data, LocalTime horaInicio, LocalTime horaFim, String nomeProfessor){

        super("O professor " + nomeProfessor + " já possui um agendamento em outra sala no dia: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " no horário das: " + horaInicio.format(DateTimeFormatter.ofPattern("HH:mm")) + " as " + horaFim.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

}
