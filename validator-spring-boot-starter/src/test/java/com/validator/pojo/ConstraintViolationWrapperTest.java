package com.validator.pojo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class ConstraintViolationWrapperTest {
    @Test
    void testToString() {
        ConstraintViolationWrapper<?> constraintViolationWrapper = new ConstraintViolationWrapper<>(null);
        constraintViolationWrapper.setTranslatedMessage("TranslatedMessage");
        constraintViolationWrapper.setTranslatedErrorFieldName("TranslatedErrorFieldName");
        constraintViolationWrapper.setLocale(Locale.ENGLISH);
        String string = constraintViolationWrapper.toString();
        String expectedValue = """
                TranslatedErrorFieldName: TranslatedErrorFieldName.
                TranslatedMessage: TranslatedMessage.
                Locale: en.
                """;
        Assertions.assertEquals(expectedValue, string);
    }
}
