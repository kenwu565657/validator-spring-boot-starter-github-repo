package com.validator.service;

import com.validator.config.ValidatorAutoConfiguration;
import com.validator.test.AddressForm;
import com.validator.test.SelfCollectionForm;
import com.validator.test.ShoppingItem;
import com.validator.test.TestingOnlineShoppingForm;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ValidatorAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(ValidationServiceImplTest.class);

    @Autowired
    private ValidationService validationService;

    @Nested
    class TestValidate {
        @Test
        void validateByNoSpecificLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm);
            for (var constraintViolationWrapper : validationResult) {
                logger.info(constraintViolationWrapper.toString());
            }
        }

        @Test
        void validateByEnglishLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.ENGLISH);
            for (var constraintViolationWrapper : validationResult) {
                logger.info(constraintViolationWrapper.toString());
            }
        }

        @Test
        void validateByTraditionalChineseLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.TRADITIONAL_CHINESE);
            for (var constraintViolationWrapper : validationResult) {
                logger.info(constraintViolationWrapper.toString());
            }
        }

        @Test
        void validateBySimplifiedChineseLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.SIMPLIFIED_CHINESE);
            for (var constraintViolationWrapper : validationResult) {
                logger.info(constraintViolationWrapper.toString());
            }
        }
    }

    private TestingOnlineShoppingForm getTestingOnlineShoppingForm() {
        TestingOnlineShoppingForm testingOnlineShoppingForm = new TestingOnlineShoppingForm();
        AddressForm addressForm = new AddressForm();
        testingOnlineShoppingForm.setAddressForm(addressForm);
        SelfCollectionForm selfCollectionForm = new SelfCollectionForm();
        testingOnlineShoppingForm.setSelfCollectionForm(selfCollectionForm);
        ShoppingItem shoppingItem = new ShoppingItem();
        List<ShoppingItem> shoppingItemList = new ArrayList<>();
        shoppingItemList.add(shoppingItem);
        testingOnlineShoppingForm.setShoppingItemList(shoppingItemList);
        return testingOnlineShoppingForm;
    }

    private <T> void logConstraintViolation(Set<ConstraintViolation<T>> constraintViolationSet) {
        for (var constraintViolation : constraintViolationSet) {
            logConstraintViolation(constraintViolation);
        }
    }

    private <T> void logConstraintViolation(ConstraintViolation<T> constraintViolation) {
        logger.info("Property Path Name: {}", constraintViolation.getPropertyPath().toString());
        logger.info("Message: {}", constraintViolation.getMessage());
    }
}