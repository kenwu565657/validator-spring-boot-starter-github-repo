package com.validator.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public class CustomizedValidator {
    private final Validator validator;

    public CustomizedValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }

    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }
}
