package com.validator.annotation;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.stream;

public class FieldNameExtractor {

    private final String fieldSeparator;
    private final int listLength;
    private final String listItemPlaceHolder;
    private final String nodeSeparator = ".";

    public FieldNameExtractor() {
        this(" - ", 5, "Item Number");
    }

    public FieldNameExtractor(String fieldSeparator, int listLength, String listItemPlaceHolder) {
        this.fieldSeparator = fieldSeparator;
        this.listLength = listLength;
        this.listItemPlaceHolder = listItemPlaceHolder;
    }

    public <T> Map<String, String> getFieldNames(Class<T> clazz) {
        String parentFieldName = getClassName(clazz);
        List<String> parentFieldNameList = new ArrayList<>();
        parentFieldNameList.add(parentFieldName);
        List<String> parentNodeNameList = new ArrayList<>();
        return getFieldNameRecursively(clazz, parentFieldNameList, parentNodeNameList);
    }

    private <T> Map<String, String> getFieldNameRecursively(Class<T> clazz, List<String> parentFieldNameList, List<String> parentNodeNameList) {
        Field[] fieldArray = clazz.getDeclaredFields();
        Map<String, String> nodeNameFieldNameRootMap = new HashMap<>();
        for (Field field : fieldArray) {
            List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
            List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
            Map<String, String> nodeNameFieldNameMap = getFieldNameRecursively(field, parentFieldNameListCopy, parentNodeNameListCopy);
            nodeNameFieldNameRootMap.putAll(nodeNameFieldNameMap);
        }
        List<Method> methodList = Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AssertFalse.class) || method.isAnnotationPresent(AssertTrue.class))
                .toList();
        for (Method method : methodList) {
            List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
            List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
            String methodFieldName = getMethodName(method);
            parentFieldNameListCopy.add(methodFieldName);
            String methodNodeName = getNodeName(method);
            parentNodeNameListCopy.add(methodNodeName);
            Map.Entry<String, String> nodeNameFieldNameEntry = Map.entry(
                    String.join(nodeSeparator, parentNodeNameListCopy),
                    String.join(fieldSeparator, parentFieldNameListCopy)
            );
            nodeNameFieldNameRootMap.put(nodeNameFieldNameEntry.getKey(), nodeNameFieldNameEntry.getValue());
        }
        return nodeNameFieldNameRootMap;
    }

    private Map<String, String> getFieldNameRecursively(Field field, List<String> parentFieldNameList, List<String> parentNodeNameList) {
        if (isPrimitiveOrWrappedOrStringOrEnum(field.getType())) {
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            String leafFieldName = getFieldName(field);
            parentFieldNameList.add(leafFieldName);

            Map.Entry<String, String> nodeNameFieldNameEntry = Map.entry(
                    String.join(nodeSeparator, parentNodeNameList),
                    String.join(fieldSeparator, parentFieldNameList)
            );
            return Map.ofEntries(nodeNameFieldNameEntry);
        }
        else if (Collection.class.isAssignableFrom(field.getType())) {
            String s = field.toGenericString();
            String type = s.split("<")[1].split(">")[0];
            Class<?> clazz;
            try {
                clazz = Class.forName(type);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            Map<String, String> listRootFieldNameMap = new HashMap<>();
            String parentFieldNameListJoinName = String.join(fieldSeparator, parentFieldNameList);
            String parentNodeNameListJoinName = String.join(nodeSeparator, parentNodeNameList);
            String listNodeName = parentNodeNameList.isEmpty() ? getNodeName(field) : MessageFormat.format("{0}{1}{2}", parentNodeNameListJoinName, nodeSeparator, getNodeName(field));
            String listFieldName = parentFieldNameList.isEmpty() ? getFieldName(field) : MessageFormat.format("{0}{1}{2}", parentFieldNameListJoinName, fieldSeparator, getFieldName(field));
            listRootFieldNameMap.put(listNodeName, listFieldName);
            for (int i = 0; i < listLength; i++) {
                List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
                List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
                String turnParentNodeName = MessageFormat.format("{0}[{1}]", listNodeName, i);
                String turnParentFieldName = MessageFormat.format("{0}{1}{2} {3}", listFieldName, fieldSeparator, listItemPlaceHolder, i + 1);
                parentNodeNameListCopy.add(turnParentNodeName);
                parentFieldNameListCopy.add(turnParentFieldName);
                AnnotatedType annotatedType = field.getAnnotatedType();
                AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
                AnnotatedType annotatedActualTypeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
                String annotatedTypedFieldName = getFieldName(annotatedActualTypeArgument);
                parentFieldNameListCopy.add(annotatedTypedFieldName);
                Map<String, String> listTurnFieldNameMap = getFieldNameRecursively(clazz, parentFieldNameListCopy, parentNodeNameListCopy);
                listRootFieldNameMap.putAll(listTurnFieldNameMap);
            }

            return listRootFieldNameMap;
        } else {
            String leafFieldName = getFieldName(field);
            parentFieldNameList.add(leafFieldName);
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            return getFieldNameRecursively(field.getType(), parentFieldNameList, parentNodeNameList);
        }
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(field);
        }
        if (field.getType().isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(field.getType());
        }
        return getNodeName(field);
    }

    private <T> String getClassName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(clazz);
        }
        return getNodeName(clazz);
    }

    private <T> String getNodeName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    private String getMethodName(Method method) {
        if (method.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(method);
        }
        return getNodeName(method);
    }

    private String getNodeName(Method method) {
        String methodName = method.getName();
        return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
    }

    private String getFieldNameForFieldNameAnnotatedElement(AnnotatedElement annotatedElement) {
        FieldName[] fieldNameAnnotationArray = annotatedElement.getAnnotationsByType(FieldName.class);
        List<String> fieldNameValueList = stream(fieldNameAnnotationArray).map(FieldName::value).toList();
        return String.join(nodeSeparator, fieldNameValueList);
    }

    private String getNodeName(Field field) {
        return field.getName();
    }

    private String getFieldName(AnnotatedType annotatedType) {
        if (annotatedType.isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(annotatedType);
        }
        if (annotatedType.getClass().isAnnotationPresent(FieldName.class)) {
            return getFieldNameForFieldNameAnnotatedElement(annotatedType.getClass());
        }
        return "Item";
    }

    private boolean isPrimitiveOrWrappedOrStringOrEnum(Class<?> clazz) {
        return clazz.isPrimitive() || isWrapperType(clazz) || String.class.equals(clazz) || clazz.isEnum();
    }

    private final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    private Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
}
