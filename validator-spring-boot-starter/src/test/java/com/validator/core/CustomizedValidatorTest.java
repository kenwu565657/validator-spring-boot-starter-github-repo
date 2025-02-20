package com.validator.core;

import com.validator.annotation.FieldNameRoot;
import com.validator.config.ValidatorAutoConfiguration;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ValidatorAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomizedValidatorTest {

    @Autowired
    private CustomizedValidator customizedValidator;

    @Nested
    class TestValidate {
        @Test
        void validate() {
            var testingObject = new TestingObject();
            var validation = customizedValidator.validate(testingObject);
            Assertions.assertFalse(validation.isEmpty());
        }
    }

    @Test
    void testValidate() {
    }

    @FieldNameRoot
    public static class TestingObject {
        @NotBlank
        private String name;

    }
}