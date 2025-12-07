package com.fatec.itu.agendasalas.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<SenhaValida, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return 
        value!= null
        && value.length() >= 8 
        && value.matches(".*[A-Z].*") 
        && value.matches(".*[a-z].*") 
        && value.matches(".*[0-9].*") 
        && value.matches(".*[^a-zA-Z0-9].*");
        
    }

}
