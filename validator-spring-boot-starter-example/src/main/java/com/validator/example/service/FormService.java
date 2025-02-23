package com.validator.example.service;

import com.validator.pojo.ConstraintViolationWrapper;
import com.validator.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Service
public class FormService {
    private final ValidationService validatorService;

    public FormService(ValidationService validatorService) {
        this.validatorService = validatorService;
    }

    public <T> Set<ConstraintViolationWrapper<T>> validateForm(T object, Locale locale, Class<?>... groups) {
        return validatorService.validate(object, locale, groups);
    }
}
