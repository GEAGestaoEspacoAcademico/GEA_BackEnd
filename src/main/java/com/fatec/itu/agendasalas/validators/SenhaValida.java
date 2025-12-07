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
@Constraint(validatedBy = SenhaValidator.class)
public @interface SenhaValida {
    String message() default "Senha fora do padrão. Ex de senha: ExemploSenha123!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
