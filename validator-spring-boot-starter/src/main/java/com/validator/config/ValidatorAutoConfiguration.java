package com.validator.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Validator validator(ValidatorFactory validatorFactory) {
        return validatorFactory.getValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    ValidatorFactory validatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
