package com.fatec.itu.agendasalas.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataInicialMaiorQueDataFinalException extends RuntimeException{

    public DataInicialMaiorQueDataFinalException(LocalDate dataInicial, LocalDate dataFinal) {
        super(String.format(
            "Intervalo de datas inválido: dataInicial (%s) é maior que dataFinal (%s)",
            dataInicial.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            dataFinal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        ));
    }



}
