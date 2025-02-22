package com.validator.service.helper;

import com.validator.annotation.FieldNameExtractor;
import com.validator.pojo.ConstraintViolationWrapper;
import jakarta.validation.ConstraintViolation;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintViolationMessageTranslator {
    public ConstraintViolationMessageTranslator() {}

    public <T> Set<ConstraintViolationWrapper<T>> translate(Set<ConstraintViolation<T>> constraintViolationSet) {
        return constraintViolationSet.stream().map(this::translate).collect(Collectors.toSet());
    }

    public <T> Set<ConstraintViolationWrapper<T>> translate(Set<ConstraintViolation<T>> constraintViolationSet, Locale locale) {
        return constraintViolationSet.stream().map(constraintViolation -> translate(constraintViolation, locale)).collect(Collectors.toSet());
    }

    public <T> ConstraintViolationWrapper<T> translate(ConstraintViolation<T> constraintViolation) {
        Locale locale = Locale.getDefault();
        return translate(constraintViolation, locale);
    }

    public <T> ConstraintViolationWrapper<T> translate(ConstraintViolation<T> constraintViolation, Locale locale) {
        ConstraintViolationWrapper<T> constraintViolationWrapper = new ConstraintViolationWrapper<>(constraintViolation);
        constraintViolationWrapper.setLocale(locale);
        Map<String, String> fieldNameMap = new FieldNameExtractor().getFieldNames(constraintViolation.getRootBeanClass(), locale);
        String translatedErrorFieldFieldName;
        if (fieldNameMap.containsKey(constraintViolation.getPropertyPath().toString())) {
            translatedErrorFieldFieldName = fieldNameMap.get(constraintViolation.getPropertyPath().toString());
        } else {
            translatedErrorFieldFieldName = constraintViolation.getPropertyPath().toString();
        }
        constraintViolationWrapper.setTranslatedErrorFieldName(translatedErrorFieldFieldName);
        constraintViolationWrapper.setTranslatedMessage(constraintViolation.getMessage());
        return constraintViolationWrapper;
    }

}
