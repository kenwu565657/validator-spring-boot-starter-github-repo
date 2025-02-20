package com.validator.annotation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FieldNameExtractorTest {

    private final Logger logger = LoggerFactory.getLogger(FieldNameExtractorTest.class);

    @Nested
    public class testGetFieldNames {
        @Test
        void getFieldNames() {
            var fieldNameExtractor = new FieldNameExtractor();
            Map<String, String> fieldNameSet = fieldNameExtractor.getFieldNames(TestingObject.class);
            logger.info(fieldNameSet.toString());
        }
    }

    @FieldNameRoot
    public static class TestingObject {
        @FieldName("name1")
        public String name;

        public String name2;

        @FieldName("testingObject2")
        public TestingObject2 testingObject2;

        @FieldName("nameList2")
        public List<@FieldName("name4") String> nameList;

        public static class Field {
            public static String name = "name";
            public static String name2 = "name2";
        }
    }

    public static class TestingObject2 {
        @FieldName("name3")
        public String name;

        @FieldName("nameList")
        public List<@FieldName("name4") String> nameList;
    }

}