package com.validator.example;

import com.validator.service.ValidationService;
import com.validator.service.ValidationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidatorSpringBootStarterExampleApplicationTests {

    @Autowired
    private ValidationService validationService;

    @Test
    void testInjectValidationService() {
        Assertions.assertNotNull(validationService);
        Assertions.assertInstanceOf(ValidationService.class, validationService);
        Assertions.assertInstanceOf(ValidationServiceImpl.class, validationService);
    }
}
