package com.fatec.itu.agendasalas.entity;

import java.time.DayOfWeek;

public enum DiaDaSemana {
     
    SEGUNDA(DayOfWeek.MONDAY),
    TERÇA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY),
    DOMINGO(DayOfWeek.SUNDAY);

    private final DayOfWeek dayOfWeek;

    DiaDaSemana(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek toJavaDay() {
        return this.dayOfWeek;
    }
}
