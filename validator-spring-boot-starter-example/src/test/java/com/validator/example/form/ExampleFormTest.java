package com.validator.example.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.validator.core.FixedLocaleResolver;
import com.validator.pojo.ConstraintViolationWrapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ExampleFormTest {
    private final Logger logger = LoggerFactory.getLogger(ExampleFormTest.class);

    @Test
    void validateAddress_conditional() {
        ExampleForm form = new ExampleForm();
        form.setLastname("lastname");
        form.setFirstname("firstname");
        form.setMailReceiverFullName("mailReceiverFullName");
        form.setAddress(new ExampleForm.Address());
        form.setCollectionMethod(ExampleForm.CollectionMethod.MAIL);
        var validator = buildValidator();
        Set<ConstraintViolation<ExampleForm>> validationResult;
        if (form.getCollectionMethod() == ExampleForm.CollectionMethod.MAIL) {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsMailGroup.class);
        } else {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsInPersonGroup.class);
        }
        Assertions.assertEquals(3, validationResult.size());
        var constraintViolationMap = toMap(validationResult);
        assertInMap(constraintViolationMap, "address.flat", "must not be blank.");
        assertInMap(constraintViolationMap, "address.street", "must not be blank.");
        assertInMap(constraintViolationMap, "address.city", "must not be blank.");

        form.setCollectionMethod(ExampleForm.CollectionMethod.IN_PERSON);
        if (form.getCollectionMethod() == ExampleForm.CollectionMethod.MAIL) {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsMailGroup.class);
        } else {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsInPersonGroup.class);
        }
        Assertions.assertEquals(0, validationResult.size());
    }

    @Test
    void validateAddress() {
        ExampleForm form = new ExampleForm();
        form.setLastname("lastname");
        form.setFirstname("firstname");
        form.setAddress(null);
        var validator = buildValidator();
        Set<ConstraintViolation<ExampleForm>> validationResult = validator.validate(form);
        Assertions.assertFalse(validationResult.isEmpty());
        var constraintViolation = validationResult.iterator().next();
        Assertions.assertEquals("address", constraintViolation.getPropertyPath().toString());
        Assertions.assertEquals("must not be null.", validationResult.iterator().next().getMessage());

        form.setAddress(new ExampleForm.Address());
        validationResult = validator.validate(form);
        Assertions.assertEquals(3, validationResult.size());
        var constraintViolationMap = toMap(validationResult);
        assertInMap(constraintViolationMap, "address.flat", "must not be blank.");
        assertInMap(constraintViolationMap, "address.street", "must not be blank.");
        assertInMap(constraintViolationMap, "address.city", "must not be blank.");
    }

    private <K, V> void assertInMap(Map<K, V> map, K key, V value) {
        Assertions.assertTrue(map.containsKey(key));
        Assertions.assertEquals(value, map.get(key));
    }

    private <T> Map<String, String> toMap(Set<ConstraintViolation<T>> constraintViolationSet) {
        return constraintViolationSet.stream().collect(Collectors.toMap(x -> x.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }

    @Test
    void validateConditionalMailReceiverName() {
        ExampleForm form = new ExampleForm();
        // set valid value for fields besides mailReceiverName
        form.setLastname("lastname");
        form.setFirstname("firstname");
        form.setCollectionMethod(ExampleForm.CollectionMethod.MAIL);
        form.setMailReceiverFullName("");

        var validator = buildValidator();
        Set<ConstraintViolation<ExampleForm>> validationResult;
        if (form.getCollectionMethod() == ExampleForm.CollectionMethod.MAIL) {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsMailGroup.class);
        } else {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsInPersonGroup.class);
        }
        Assertions.assertEquals(1, validationResult.size());
        var constraintViolation = validationResult.iterator().next();
        Assertions.assertEquals("mailReceiverFullName", constraintViolation.getPropertyPath().toString());
        Assertions.assertEquals("must not be null.", validationResult.iterator().next().getMessage());

        form.setCollectionMethod(ExampleForm.CollectionMethod.IN_PERSON);

        if (form.getCollectionMethod() == ExampleForm.CollectionMethod.MAIL) {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsMailGroup.class);
        } else {
            validationResult = validator.validate(form, ExampleForm.CollectionMethodIsInPersonGroup.class);
        }
        Assertions.assertEquals(0, validationResult.size());
    }

    @Test
    void toJson() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = objectWriter.writeValueAsString(new ExampleForm());
        Assertions.assertEquals(
                """
                        {
                          "firstname" : null,
                          "lastname" : null,
                          "collectionMethod" : null,
                          "mailReceiverFullName" : null,
                          "address" : null,
                          "firstnameAndLastnameSameLanguage" : true
                        } 
                        """.trim(),
                jsonString
        );
    }

    @Test
    void validate_AssertTrue() {
        var validator = buildValidator();
        var exampleForm = new ExampleForm();
        exampleForm.setFirstname("John");
        exampleForm.setLastname("陳");
        Set<ConstraintViolation<ExampleForm>> validationResult = validator.validate(exampleForm);
        Assertions.assertEquals(1, validationResult.size());
        var constraintViolation = validationResult.iterator().next();
        Assertions.assertEquals("firstnameAndLastnameSameLanguage", constraintViolation.getPropertyPath().toString());
        Assertions.assertEquals("Firstname And Lastname must in same language.", validationResult.iterator().next().getMessage());
    }

    @Test
    void validateFirstnameAndLastnameSameLanguage() {
        var validator = buildValidator();
        var exampleForm = new ExampleForm();
        exampleForm.setFirstname("John");
        exampleForm.setLastname("陳");
        Set<ConstraintViolation<ExampleForm>> validationResult = validator.validate(exampleForm);
        Assertions.assertEquals(1, validationResult.size());
        Assertions.assertEquals("Firstname And Lastname must in same language.", validationResult.iterator().next().getMessage());
    }

    @Test
    void testLocale() {
        var englishValidator = buildValidator(Locale.ENGLISH);
        var englishValidationResult = englishValidator.validate(new SimplePoJo());
        Assertions.assertEquals(1, englishValidationResult.size());
        Assertions.assertEquals("must not be blank", englishValidationResult.iterator().next().getMessage());
        var chineseValidator = buildValidator(Locale.TRADITIONAL_CHINESE);
        var chineseValidationResult = chineseValidator.validate(new SimplePoJo());
        Assertions.assertEquals(1, englishValidationResult.size());
        Assertions.assertEquals("不得空白", chineseValidationResult.iterator().next().getMessage());
    }

    static class SimplePoJo {
        @NotBlank
        private String name;
    }

    private Validator buildValidator() {
        return Validation.byProvider( HibernateValidator.class )
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }

    private Validator buildValidator(Locale locale) {
        return Validation.byProvider( HibernateValidator.class )
                .configure()
                .messageInterpolator(
                        new ParameterMessageInterpolator(Set.of(locale), locale, FixedLocaleResolver.of(locale), false)
                )
                .buildValidatorFactory()
                .getValidator();
    }

}