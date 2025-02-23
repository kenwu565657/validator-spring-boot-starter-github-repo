package com.validator.service;

import com.validator.core.FieldNameExtractor;
import com.validator.core.FixedLocaleResolver;
import com.validator.pojo.ConstraintViolationWrapper;
import com.validator.core.ConstraintViolationMessageTranslator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ValidationServiceImpl implements ValidationService {
    private final ConstraintViolationMessageTranslator constraintViolationMessageTranslator;
    private final Map<Locale, Validator> validatorPool = new ConcurrentHashMap<>();
    private final Locale defaultLocale = Locale.ENGLISH;

    public ValidationServiceImpl() {
        FieldNameExtractor fieldNameExtractor = new FieldNameExtractor();
        this.constraintViolationMessageTranslator = new ConstraintViolationMessageTranslator(fieldNameExtractor);
        Validator validator = buildValidator(defaultLocale);
        validatorPool.put(defaultLocale, validator);
    }

    @Override
    public <T> Set<ConstraintViolationWrapper<T>> validate(T object, Class<?>... groups) {
        Validator validator = getValidator();
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(object, groups);
        return constraintViolationMessageTranslator.translate(constraintViolationSet);
    }

    @Override
    public <T> Set<ConstraintViolationWrapper<T>> validate(T object, Locale locale, Class<?>... groups) {
        Validator validator = getValidator(locale);
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(object, groups);
        return constraintViolationMessageTranslator.translate(constraintViolationSet, locale);
    }

    private Validator getValidator() {
        return validatorPool.get(defaultLocale);
    }

    private Validator getValidator(Locale locale) {
        if (validatorPool.containsKey(locale)) {
            return validatorPool.get(locale);
        }
        Validator validator = buildValidator(locale);
        validatorPool.put(locale, validator);
        return validator;
    }

    private Validator buildValidator(Locale locale) {
        return Validation.byProvider( HibernateValidator.class )
                .configure()
                .messageInterpolator(
                        new ParameterMessageInterpolator(Set.of(locale), locale, FixedLocaleResolver.of(locale), false)
                )
                .buildValidatorFactory()
                .getValidator();
    }
}
