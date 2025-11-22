package com.fatec.itu.agendasalas.entity;

import java.time.DayOfWeek;
import java.text.Normalizer;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DiaDaSemana {
     
    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
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

    @JsonCreator
    public static DiaDaSemana fromString(String value) {
        if (value == null) return null;
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        normalized = normalized.trim().toUpperCase();
        return DiaDaSemana.valueOf(normalized);
    }
}
