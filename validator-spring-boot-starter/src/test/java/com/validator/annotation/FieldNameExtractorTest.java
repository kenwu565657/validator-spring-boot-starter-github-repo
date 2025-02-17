package com.validator.annotation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FieldNameExtractorTest {

    @Nested
    public class testGetFieldName {

        @Test
        void testGetFieldNameWithFieldNameAnnotation() {
            var testingObject = new TestingObject();
            var fieldNameExtractor = new FieldNameExtractor();

            String fieldName;
            try {
                fieldName = fieldNameExtractor.getFieldName(testingObject.getClass().getDeclaredField(TestingObject.Field.name));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            assertEquals("name1", fieldName);
        }

        @Test
        void testGetFieldNameWithoutFieldNameAnnotation() {
            var testingObject = new TestingObject();
            var fieldNameExtractor = new FieldNameExtractor();

            String fieldName;
            try {
                fieldName = fieldNameExtractor.getFieldName(testingObject.getClass().getDeclaredField(TestingObject.Field.name2));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            assertEquals("name2", fieldName);

        }

    }

    public static class TestingObject {
        @FieldName("name1")
        public String name;

        public String name2;

        public static class Field {
            public static String name = "name";
            public static String name2 = "name2";
        }
    }

}