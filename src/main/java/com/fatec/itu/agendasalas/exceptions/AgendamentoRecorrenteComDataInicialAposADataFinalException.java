package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AgendamentoRecorrenteComDataInicialAposADataFinalException extends RuntimeException{

    public AgendamentoRecorrenteComDataInicialAposADataFinalException(LocalDate dataInicial, LocalDate dataFinal) {
        super("Tentativa de cadastrar um agendamento recorrente da secretaria com data inicial maior que data final: \n"+
            "data inicial: " + dataInicial.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
            "data final: " + dataFinal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) 
        );
    }



}
