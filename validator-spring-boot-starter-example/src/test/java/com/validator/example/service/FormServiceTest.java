package com.validator.example.service;

import com.validator.example.form.SimpleForm;
import com.validator.pojo.ConstraintViolationWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class FormServiceTest {

    private final Logger logger = LoggerFactory.getLogger(FormServiceTest.class);

    @Autowired
    private FormService formService;

    @Nested
    class TestValidateForm {
        @Test
        void testValidateFormForEnglishLocale() {
            SimpleForm simpleForm = getEmptySimpleForm();
            var constraintViolationWrapperSet = formService.validateForm(simpleForm, Locale.ENGLISH);
            Assertions.assertEquals(5, constraintViolationWrapperSet.size());
            printErrorMessage(constraintViolationWrapperSet);
            assertLocale(constraintViolationWrapperSet, Locale.ENGLISH);
            var errorMessageMap = toMap(constraintViolationWrapperSet);
            assertInMap(errorMessageMap, "A Simple Form - Contact Email Address", "must not be blank");
            assertInMap(errorMessageMap, "A Simple Form - Simple Form Id", "must not be blank");
            assertInMap(errorMessageMap, "A Simple Form - Contact Last Name", "must not be blank");
            assertInMap(errorMessageMap, "A Simple Form - Contact Phone Number", "must not be blank");
            assertInMap(errorMessageMap, "A Simple Form - Contact First Name", "must not be blank");
        }

        @Test
        void testValidateFormForTraditionalChineseLocale() {
            SimpleForm simpleForm = getEmptySimpleForm();
            var constraintViolationWrapperSet = formService.validateForm(simpleForm, Locale.TRADITIONAL_CHINESE);
            Assertions.assertEquals(5, constraintViolationWrapperSet.size());
            printErrorMessage(constraintViolationWrapperSet);
            assertLocale(constraintViolationWrapperSet, Locale.TRADITIONAL_CHINESE);
            var errorMessageMap = toMap(constraintViolationWrapperSet);
            assertInMap(errorMessageMap, "簡單表格 - 簡單表格點碼", "不得空白");
            assertInMap(errorMessageMap, "簡單表格 - 聯絡姓名 (名字)", "不得空白");
            assertInMap(errorMessageMap, "簡單表格 - 聯絡姓名 (姓)", "不得空白");
            assertInMap(errorMessageMap, "簡單表格 - 聯絡電郵", "不得空白");
            assertInMap(errorMessageMap, "簡單表格 - 聯絡電話號碼", "不得空白");
        }

        @Test
        void testValidateFormForSimplifiedChineseLocale() {
            SimpleForm simpleForm = getEmptySimpleForm();
            var constraintViolationWrapperSet = formService.validateForm(simpleForm, Locale.SIMPLIFIED_CHINESE);
            Assertions.assertEquals(5, constraintViolationWrapperSet.size());
            printErrorMessage(constraintViolationWrapperSet);
            assertLocale(constraintViolationWrapperSet, Locale.SIMPLIFIED_CHINESE);
            var errorMessageMap = toMap(constraintViolationWrapperSet);
            assertInMap(errorMessageMap, "简单表格 - 联系电邮", "不能为空");
            assertInMap(errorMessageMap, "简单表格 - 联系电话号码", "不能为空");
            assertInMap(errorMessageMap, "简单表格 - 简单表格点码", "不能为空");
            assertInMap(errorMessageMap, "简单表格 - 联系姓名 (姓)", "不能为空");
            assertInMap(errorMessageMap, "简单表格 - 联系姓名 (名字)", "不能为空");
        }
    }

    private SimpleForm getEmptySimpleForm() {
        return new SimpleForm();
    }

    private <T> void assertLocale(Set<ConstraintViolationWrapper<T>> constraintViolationWrapperSet, Locale locale) {
        for (ConstraintViolationWrapper<T> constraintViolationWrapper : constraintViolationWrapperSet) {
            Assertions.assertEquals(locale, constraintViolationWrapper.getLocale());
        }
    }

    private <K, V> void assertInMap(Map<K, V> map, K key, V value) {
        Assertions.assertTrue(map.containsKey(key));
        Assertions.assertEquals(value, map.get(key));
    }

    private <T> Map<String, String> toMap(Set<ConstraintViolationWrapper<T>> constraintViolationWrapperSet) {
        return constraintViolationWrapperSet.stream().collect(Collectors.toMap(ConstraintViolationWrapper::getTranslatedErrorFieldName, ConstraintViolationWrapper::getTranslatedMessage));
    }

    private <T> void printErrorMessage(Set<ConstraintViolationWrapper<T>> constraintViolationWrapperSet) {
        for (ConstraintViolationWrapper<T> constraintViolationWrapper : constraintViolationWrapperSet) {
            printErrorMessage(constraintViolationWrapper);
        }
    }

    private <T> void printErrorMessage(ConstraintViolationWrapper<T> constraintViolationWrapper) {
        logger.info(constraintViolationWrapper.toString());
    }
}
