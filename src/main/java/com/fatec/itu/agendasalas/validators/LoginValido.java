package com.fatec.itu.agendasalas.validators;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
public @interface LoginValido {
    String message() default "Login fora do padrão permitido. Sem acentos, sem caracteres especiais com exceção de .";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
