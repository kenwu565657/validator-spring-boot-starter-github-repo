package com.validator.annotation;

import com.validator.test.TestingOnlineShoppingForm;
import com.validator.test.TestingOnlineShoppingFormNotAnnotated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                            "Online Shopping Form - Address Information - Address Line 1"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line2",
                            "Online Shopping Form - Address Information - Address Line 2"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.line3",
                            "Online Shopping Form - Address Information - Address Line 3"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.district",
                            "Online Shopping Form - Address Information - Address District"
                    )
            );
            assertContainEntry(fieldNameMap,
                    Map.entry(
                            "addressForm.eitherAddressLineProvided",
                            "Online Shopping Form - Address Information - Address Line 1 Or 2 Or 3"
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
