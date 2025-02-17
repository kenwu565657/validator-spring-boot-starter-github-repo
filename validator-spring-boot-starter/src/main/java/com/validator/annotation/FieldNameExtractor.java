package com.validator.annotation;

import java.lang.reflect.Field;

public class FieldNameExtractor {
    public String getFieldName(Field field) {
        if (field.isAnnotationPresent(FieldName.class)) {
            return field.getAnnotation(FieldName.class).value();
        }
        return field.getName();
    }
}
