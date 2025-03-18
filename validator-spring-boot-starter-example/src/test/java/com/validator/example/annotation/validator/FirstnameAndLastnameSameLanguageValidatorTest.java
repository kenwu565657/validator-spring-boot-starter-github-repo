package com.validator.example.annotation.validator;

import com.validator.example.form.ExampleForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FirstnameAndLastnameSameLanguageValidatorTest {
    @Test
    void isValid() {
        var validator = new FirstnameAndLastnameSameLanguageValidator();
        ExampleForm form = new ExampleForm();
        form.setFirstname("John");
        form.setLastname("Doe");
        Assertions.assertTrue(validator.isValid(form, null));

        form.setFirstname("John John");
        form.setLastname("Doe");
        Assertions.assertTrue(validator.isValid(form, null));

        form.setFirstname("小明");
        form.setLastname("陳");
        Assertions.assertTrue(validator.isValid(form, null));

        form.setFirstname("小明");
        form.setLastname("chan");
        Assertions.assertFalse(validator.isValid(form, null));

        form.setFirstname("John John");
        form.setLastname("陳");
        Assertions.assertFalse(validator.isValid(form, null));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "John,Doe,true",
            "John John,Doe,true",
            "小明,陳,true"
    }
    )
    void isValid(String firstname, String lastname, boolean isValid) {
        var validator = new FirstnameAndLastnameSameLanguageValidator();
        ExampleForm exampleForm = new ExampleForm();
        exampleForm.setFirstname(firstname);
        exampleForm.setLastname(lastname);
        if (isValid) {
            Assertions.assertTrue(validator.isValid(exampleForm, null));
        } else {
            Assertions.assertFalse(validator.isValid(exampleForm, null));
        }
    }
}