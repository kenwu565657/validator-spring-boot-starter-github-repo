package com.validator.config;

import com.validator.service.ValidationService;
import com.validator.service.ValidationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorAutoConfiguration {
    @Bean
    ValidationService validationService() {
        return new ValidationServiceImpl();
    }
}
