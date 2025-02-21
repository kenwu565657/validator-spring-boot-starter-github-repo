package com.validator.core;

import com.validator.config.ValidatorAutoConfiguration;
import com.validator.test.AddressForm;
import com.validator.test.SelfCollectionForm;
import com.validator.test.ShoppingItem;
import com.validator.test.TestingOnlineShoppingForm;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = {ValidatorAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomizedValidatorTest {

    private final Logger logger = LoggerFactory.getLogger(CustomizedValidatorTest.class);

    @Autowired
    private CustomizedValidator customizedValidator;

    @Nested
    class TestValidate {
        @Test
        void validate() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = new TestingOnlineShoppingForm();
            AddressForm addressForm = new AddressForm();
            testingOnlineShoppingForm.setAddressForm(addressForm);
            SelfCollectionForm selfCollectionForm = new SelfCollectionForm();
            testingOnlineShoppingForm.setSelfCollectionForm(selfCollectionForm);
            ShoppingItem shoppingItem = new ShoppingItem();
            List<ShoppingItem> shoppingItemList = new ArrayList<>();
            shoppingItemList.add(shoppingItem);
            testingOnlineShoppingForm.setShoppingItemList(shoppingItemList);
            var constraintViolationSet = customizedValidator.validate(testingOnlineShoppingForm);
            Assertions.assertFalse(constraintViolationSet.isEmpty());
            logConstraintViolation(constraintViolationSet);
        }
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
