package com.validator.service;

import com.validator.config.ValidatorAutoConfiguration;
import com.validator.pojo.ConstraintViolationWrapper;
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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            printConstraintViolationWrapper(validationResult);
            assertLocale(validationResult, Locale.getDefault());
            Map<String, String> validationErrorMessageMap = toMap(validationResult);
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Address Form - Address District", "must not be null");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Address Form - Address Line 1 Or 2 Or 3", "must be true");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Email Address", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact First Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Last Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Phone Number", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Self Collection Form - Self Collection Point Pin Number", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Self Collection Form - Phone Number Or Email Address", "must be true");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Selected Shipping Method", "must not be null");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Quantity", "must not be null");
        }

        @Test
        void validateByEnglishLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.ENGLISH);
            printConstraintViolationWrapper(validationResult);
            assertLocale(validationResult, Locale.ENGLISH);
            Map<String, String> validationErrorMessageMap = toMap(validationResult);
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Address Form - Address District", "must not be null");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Address Form - Address Line 1 Or 2 Or 3", "must be true");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Email Address", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact First Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Last Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Contact Phone Number", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Self Collection Form - Self Collection Point Pin Number", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Self Collection Form - Phone Number Or Email Address", "must be true");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Selected Shipping Method", "must not be null");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Name", "must not be blank");
            assertInMap(validationErrorMessageMap, "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Quantity", "must not be null");
        }

        @Test
        void validateByTraditionalChineseLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.TRADITIONAL_CHINESE);
            printConstraintViolationWrapper(validationResult);
            assertLocale(validationResult, Locale.TRADITIONAL_CHINESE);
            Map<String, String> validationErrorMessageMap = toMap(validationResult);
            assertInMap(validationErrorMessageMap, "網上購物表格 - 地址表格 - 地區", "不得是空值");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 地址表格 - 任意地址行", "必須是 true");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 聯絡電郵", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 聯絡姓名 (名字)", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 聯絡姓名 (姓)", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 聯絡電話號碼", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 自取表格 - 自取點碼", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 自取表格 - 聯絡電話號碼或聯絡電郵", "必須是 true");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 選擇的運輸方式", "不得是空值");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品名", "不得空白");
            assertInMap(validationErrorMessageMap, "網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品數目", "不得是空值");
        }

        @Test
        void validateBySimplifiedChineseLocale() {
            TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
            var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.SIMPLIFIED_CHINESE);
            printConstraintViolationWrapper(validationResult);
            assertLocale(validationResult, Locale.SIMPLIFIED_CHINESE);
            Map<String, String> validationErrorMessageMap = toMap(validationResult);
            assertInMap(validationErrorMessageMap, "网上购物表格 - 地址表格 - 地区", "不能为null");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 地址表格 - 任意地址行", "只能为true");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 联系电邮", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 联系姓名 (名字)", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 联系姓名 (姓)", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 联系电话号码", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 自取表格 - 自取点码", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 自取表格 - 联系电话号码或联系电邮", "只能为true");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 选择的运输方式", "不能为null");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品名", "不能为空");
            assertInMap(validationErrorMessageMap, "网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品数目", "不能为null");
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

    private <T> void logConstraintViolation(ConstraintViolation<T> constraintViolation) {
        logger.info("Property Path Name: {}", constraintViolation.getPropertyPath().toString());
        logger.info("Message: {}", constraintViolation.getMessage());
    }

    private <T> void printConstraintViolationWrapper(Set<ConstraintViolationWrapper<T>> constraintViolationSet) {
        List<ConstraintViolationWrapper<T>> constraintViolationWrapperList =
        constraintViolationSet
                .stream()
                .sorted(Comparator.comparing(e -> e.getConstraintViolation().getPropertyPath().toString()))
                .toList();
        for (var constraintViolation : constraintViolationWrapperList) {
            printConstraintViolationWrapper(constraintViolation);
        }
    }

    private <T> void printConstraintViolationWrapper(ConstraintViolationWrapper<T> constraintViolation) {
        System.out.println(constraintViolation);
    }
}