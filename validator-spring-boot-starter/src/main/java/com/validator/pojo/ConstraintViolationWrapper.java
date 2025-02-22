package com.validator.pojo;

import jakarta.validation.ConstraintViolation;

import java.text.MessageFormat;
import java.util.Locale;

public class ConstraintViolationWrapper<T> {
    private ConstraintViolation<T> constraintViolation;
    private Locale locale;
    private String translatedErrorFieldName;
    private String translatedMessage;

    public ConstraintViolationWrapper(ConstraintViolation<T> constraintViolation) {
        this.constraintViolation = constraintViolation;
    }

    public ConstraintViolation<T> getConstraintViolation() {
        return constraintViolation;
    }

    public void setConstraintViolation(ConstraintViolation<T> constraintViolation) {
        this.constraintViolation = constraintViolation;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTranslatedErrorFieldName() {
        return translatedErrorFieldName;
    }

    public void setTranslatedErrorFieldName(String translatedErrorFieldName) {
        this.translatedErrorFieldName = translatedErrorFieldName;
    }

    public String getTranslatedMessage() {
        return translatedMessage;
    }

    public void setTranslatedMessage(String translatedMessage) {
        this.translatedMessage = translatedMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(MessageFormat.format("TranslatedErrorFieldName: {0}", translatedErrorFieldName));
        builder.append(".\n");
        builder.append(MessageFormat.format("TranslatedMessage: {0}", translatedMessage));
        builder.append(".\n");
        builder.append(MessageFormat.format("Locale: {0}", locale));
        builder.append(".\n");
        return builder.toString();
    }
}
