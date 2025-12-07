package com.fatec.itu.agendasalas.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValido, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "^[\\w._%+-]+@(fatec\\.sp\\.gov\\.br|cps\\.sp\\.gov\\.br|etec\\.sp\\.gov\\.br)$";
        return value!=null && value.matches(regex);
    }
    

}
