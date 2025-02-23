package com.validator.core;

import com.validator.test.TestingOnlineShoppingForm;
import com.validator.test.TestingOnlineShoppingFormNotAnnotated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FieldNameExtractorTest {

    private final Logger logger = LoggerFactory.getLogger(FieldNameExtractorTest.class);

    @Nested
    public class testGetFieldNames {
        @Test
        void getFieldNameMap() {
            var fieldNameExtractor = new FieldNameExtractor();
            Map<String, String> fieldNameMap = fieldNameExtractor.getFieldNames(TestingOnlineShoppingForm.class);
            logger.info("Field Name Map: {}", fieldNameMap);
            assertContainEntry(fieldNameMap, Map.entry("id", "Online Shopping Form - Shopping Form Id"));
            assertContainEntry(fieldNameMap, Map.entry("firstname", "Online Shopping Form - Contact First Name"));
            assertContainEntry(fieldNameMap, Map.entry("lastname", "Online Shopping Form - Contact Last Name"));
            assertContainEntry(fieldNameMap, Map.entry("emailAddress", "Online Shopping Form - Contact Email Address"));
            assertContainEntry(fieldNameMap, Map.entry("phoneNumber", "Online Shopping Form - Contact Phone Number"));
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].itemId",
                            "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Name"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].quantity",
                            "Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Quantity"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shippingMethod",
                            "Online Shopping Form - Selected Shipping Method"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.collectionPointId",
                            "Online Shopping Form - Self Collection Form - Self Collection Point Pin Number"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.phoneNumber",
                            "Online Shopping Form - Self Collection Form - Contact Phone Number"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.emailAddress",
                            "Online Shopping Form - Self Collection Form - Contact Email Address"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.eitherPhoneNumberOrEmailAddressProvided",
                            "Online Shopping Form - Self Collection Form - Phone Number Or Email Address"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line1",
                            "Online Shopping Form - Address Form - Address Line 1"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line2",
                            "Online Shopping Form - Address Form - Address Line 2"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line3",
                            "Online Shopping Form - Address Form - Address Line 3"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.district",
                            "Online Shopping Form - Address Form - Address District"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.eitherAddressLineProvided",
                            "Online Shopping Form - Address Form - Address Line 1 Or 2 Or 3"
                    )
            );
        }

        @Test
        void getFieldNameMapForTraditionalChinese() {
            var fieldNameExtractor = new FieldNameExtractor();
            Map<String, String> fieldNameMap = fieldNameExtractor.getFieldNames(TestingOnlineShoppingForm.class, Locale.TRADITIONAL_CHINESE);
            logger.info("Field Name Map: {}", fieldNameMap);
            assertContainEntry(fieldNameMap, Map.entry("id", "網上購物表格 - 購物表格 ID"));
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "firstname",
                            "網上購物表格 - 聯絡姓名 (名字)"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "lastname",
                            "網上購物表格 - 聯絡姓名 (姓)"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "emailAddress",
                            "網上購物表格 - 聯絡電郵"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "phoneNumber",
                            "網上購物表格 - 聯絡電話號碼"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList",
                            "網上購物表格 - 購物車"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].itemId",
                            "網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品名"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].quantity",
                            "網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品數目"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shippingMethod",
                            "網上購物表格 - 選擇的運輸方式"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.collectionPointId",
                            "網上購物表格 - 自取表格 - 自取點碼"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.phoneNumber",
                            "網上購物表格 - 自取表格 - 聯絡電話號碼"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.emailAddress",
                            "網上購物表格 - 自取表格 - 聯絡電郵"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.eitherPhoneNumberOrEmailAddressProvided",
                            "網上購物表格 - 自取表格 - 聯絡電話號碼或聯絡電郵"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line1",
                            "網上購物表格 - 地址表格 - 地址第一行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line2",
                            "網上購物表格 - 地址表格 - 地址第二行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line3",
                            "網上購物表格 - 地址表格 - 地址第三行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.district",
                            "網上購物表格 - 地址表格 - 地區"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.eitherAddressLineProvided",
                            "網上購物表格 - 地址表格 - 任意地址行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line1",
                            "網上購物表格 - 地址資訊 - 地址第一行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line2",
                            "網上購物表格 - 地址資訊 - 地址第二行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line3",
                            "網上購物表格 - 地址資訊 - 地址第三行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.district",
                            "網上購物表格 - 地址資訊 - 地區"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.eitherAddressLineProvided",
                            "網上購物表格 - 地址資訊 - 任意地址行"
                    )
            );

        }

        @Test
        void getFieldNameMapForSimplifiedChinese() {
            var fieldNameExtractor = new FieldNameExtractor();
            Map<String, String> fieldNameMap = fieldNameExtractor.getFieldNames(TestingOnlineShoppingForm.class, Locale.SIMPLIFIED_CHINESE);
            logger.info("Field Name Map: {}", fieldNameMap);
            assertContainEntry(fieldNameMap, Map.entry("id", "网上购物表格 - 购物表格 ID"));
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "firstname",
                            "网上购物表格 - 联系姓名 (名字)"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "lastname",
                            "网上购物表格 - 联系姓名 (姓)"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "emailAddress",
                            "网上购物表格 - 联系电邮"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "phoneNumber",
                            "网上购物表格 - 联系电话号码"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList",
                            "网上购物表格 - 购物车"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].itemId",
                            "网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品名"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].quantity",
                            "网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品数目"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shippingMethod",
                            "网上购物表格 - 选择的运输方式"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.collectionPointId",
                            "网上购物表格 - 自取表格 - 自取点码"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.phoneNumber",
                            "网上购物表格 - 自取表格 - 联系电话号码"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.emailAddress",
                            "网上购物表格 - 自取表格 - 联系电邮"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.eitherPhoneNumberOrEmailAddressProvided",
                            "网上购物表格 - 自取表格 - 联系电话号码或联系电邮"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line1",
                            "网上购物表格 - 地址表格 - 地址第一行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line2",
                            "网上购物表格 - 地址表格 - 地址第二行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line3",
                            "网上购物表格 - 地址表格 - 地址第三行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.district",
                            "网上购物表格 - 地址表格 - 地区"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.eitherAddressLineProvided",
                            "网上购物表格 - 地址表格 - 任意地址行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line1",
                            "网上购物表格 - 地址信息 - 地址第一行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line2",
                            "网上购物表格 - 地址信息 - 地址第二行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line3",
                            "网上购物表格 - 地址信息 - 地址第三行"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.district",
                            "网上购物表格 - 地址信息 - 地区"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.eitherAddressLineProvided",
                            "网上购物表格 - 地址信息 - 任意地址行"
                    )
            );

        }

        @Test
        void getFieldNameMapFromNotAnnotatedClass() {
            var fieldNameExtractor = new FieldNameExtractor();
            Map<String, String> fieldNameMap = fieldNameExtractor.getFieldNames(TestingOnlineShoppingFormNotAnnotated.class);
            logger.info("Field Name Map: {}", fieldNameMap);
            assertContainEntry(fieldNameMap, Map.entry("id", "TestingOnlineShoppingFormNotAnnotated - id"));
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "firstname",
                            "TestingOnlineShoppingFormNotAnnotated - firstname"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "lastname",
                            "TestingOnlineShoppingFormNotAnnotated - lastname"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "emailAddress",
                            "TestingOnlineShoppingFormNotAnnotated - emailAddress"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "phoneNumber",
                            "TestingOnlineShoppingFormNotAnnotated - phoneNumber"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList",
                            "TestingOnlineShoppingFormNotAnnotated - shoppingItemList"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].itemId",
                            "TestingOnlineShoppingFormNotAnnotated - TestingOnlineShoppingFormNotAnnotated - shoppingItemList - Item Number 1 - Item - itemId"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shoppingItemList[0].quantity",
                            "TestingOnlineShoppingFormNotAnnotated - TestingOnlineShoppingFormNotAnnotated - shoppingItemList - Item Number 1 - Item - quantity"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "shippingMethod",
                            "TestingOnlineShoppingFormNotAnnotated - shippingMethod"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.collectionPointId",
                            "TestingOnlineShoppingFormNotAnnotated - Self Collection Form - Self Collection Point Pin Number"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.phoneNumber",
                            "TestingOnlineShoppingFormNotAnnotated - Self Collection Form - Contact Phone Number"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.emailAddress",
                            "TestingOnlineShoppingFormNotAnnotated - Self Collection Form - Contact Email Address"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "selfCollectionForm.eitherPhoneNumberOrEmailAddressProvided",
                            "TestingOnlineShoppingFormNotAnnotated - Self Collection Form - Phone Number Or Email Address"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line1",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm - line1"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line2",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm - line2"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line3",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm - line3"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.district",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm - district"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.eitherAddressLineProvided",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm - eitherAddressLineProvided"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line1",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm2 - line1"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line2",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm2 - line2"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.line3",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm2 - line3"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.district",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm2 - district"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm2.eitherAddressLineProvided",
                            "TestingOnlineShoppingFormNotAnnotated - addressForm2 - eitherAddressLineProvided"
                    )
            );
        }
    }

    private <K, V> void assertContainEntry(Map<K, V> map, Map.Entry<K, V> entry) {
        Assertions.assertTrue(map.containsKey(entry.getKey()));
        V value = map.get(entry.getKey());
        Assertions.assertNotNull(value);
        Assertions.assertEquals(entry.getValue(), value);
    }
}
