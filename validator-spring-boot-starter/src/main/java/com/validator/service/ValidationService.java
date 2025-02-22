package com.validator.service;

import com.validator.pojo.ConstraintViolationWrapper;

import java.util.Locale;
import java.util.Set;

public interface ValidationService {
    <T> Set<ConstraintViolationWrapper<T>> validate(T object, Class<?>... groups);
    <T> Set<ConstraintViolationWrapper<T>> validate(T object, Locale locale, Class<?>... groups);
}
