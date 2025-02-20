package com.validator.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldNameExtractor {
    public <T> Map<String, String> getFieldNames(Class<T> clazz) {
        List<String> parentFieldNameList = new ArrayList<>();
        List<String> parentNodeNameList = new ArrayList<>();
        return getFieldNameRecursively(clazz, parentFieldNameList, parentNodeNameList);
    }

    private Map<String, String> getFieldNameRecursively(Field field, List<String> parentFieldNameList, List<String> parentNodeNameList) {
        if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
            String leafFieldName = getFieldName(field);
            parentFieldNameList.add(leafFieldName);
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            Map.Entry<String, String> nodeNameFieldNameEntry = Map.entry(
                    String.join(".", parentNodeNameList),
                    String.join(".", parentFieldNameList)
            );
            return Map.ofEntries(nodeNameFieldNameEntry);
        }
        else if (Collection.class.isAssignableFrom(field.getType())) {
            String s = field.toGenericString();
            String type = s.split("<")[1].split(">")[0];
            Class clazz;
            try {
                clazz = Class.forName(type);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String leafFieldName = getFieldName(field);
            parentFieldNameList.add(leafFieldName);
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);

            AnnotatedType annotatedType = field.getAnnotatedType();
            AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
            AnnotatedType annotatedActualTypeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
            String annotatedTypedFieldName = getFieldName(annotatedActualTypeArgument);
            String annotatedTypedNodeName = getNodeName(annotatedActualTypeArgument);
            parentFieldNameList.add(annotatedTypedFieldName);
            parentFieldNameList.add(annotatedTypedNodeName);

            return getFieldNameRecursively(clazz, parentFieldNameList, parentNodeNameList);
        } else {
            String leafFieldName = getFieldName(field);
            parentFieldNameList.add(leafFieldName);
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            return getFieldNameRecursively(field.getType(), parentFieldNameList, parentNodeNameList);
        }
    }

    private <T> Map<String, String> getFieldNameRecursively(Class<T> clazz, List<String> parentFieldNameList, List<String> parentNodeNameList) {
        if (clazz.isPrimitive() || clazz.equals(String.class)) {
            Map.Entry<String, String> nodeNameFieldNameEntry = Map.entry(
                    String.join(".", parentNodeNameList),
                    String.join(".", parentFieldNameList)
            );
            return Map.ofEntries(nodeNameFieldNameEntry);
        }

        Field[] fieldArray = clazz.getFields();
        Map<String, String> nodeNameFieldNameRootMap = new HashMap<>();
        for (Field field : fieldArray) {
            List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
            List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
            Map<String, String> nodeNameFieldNameMap = getFieldNameRecursively(field, parentFieldNameListCopy, parentNodeNameListCopy);
            nodeNameFieldNameRootMap.putAll(nodeNameFieldNameMap);
        }
        return nodeNameFieldNameRootMap;
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(field);
        }
        return getNodeName(field);
    }

    private String getFieldNameForFieldNameAnnotatedElement(AnnotatedElement annotatedElement) {
        FieldName[] fieldNameAnnotationArray = annotatedElement.getAnnotationsByType(FieldName.class);
        List<String> fieldNameValueList = Arrays.stream(fieldNameAnnotationArray).map(FieldName::value).toList();
        return String.join(".", fieldNameValueList);
    }

    private String getNodeName(Field field) {
        return field.getName();
    }

    private String getFieldName(AnnotatedType annotatedType) {
        if (annotatedType.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(annotatedType);
        }
        return getNodeName(annotatedType);
    }

    private String getNodeName(AnnotatedType annotatedType) {
        return "item";
    }
}
