package com.validator.example.annotation;

import com.validator.example.annotation.validator.FirstnameAndLastnameSameLanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstnameAndLastnameSameLanguageValidator.class)
public @interface ValidateFirstnameAndLastnameSameLanguage {
    String message() default "Firstname And Lastname must in same language.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
