package com.fatec.itu.agendasalas.validators;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext ctx) {
        if (value == null) return true;
        return !value.isEmpty();
    }
}