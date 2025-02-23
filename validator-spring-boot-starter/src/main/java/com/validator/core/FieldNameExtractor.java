package com.validator.core;

import com.validator.annotation.FieldName;
import com.validator.constant.LocaleConstant;
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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;

public class FieldNameExtractor {

    private final String fieldSeparator;
    private final int listLength;
    private final Map<Locale, String> localeListItemPlaceHolderMap;
    private final String defaultListItemPlaceHolder = "Item Number {0}";
    private final String nodeSeparator = ".";

    public FieldNameExtractor() {
        this(" - ",
                5,
                Map.ofEntries(
                        Map.entry(Locale.ENGLISH, "Item Number {0}"),
                        Map.entry(Locale.TRADITIONAL_CHINESE, "第{0}項"),
                        Map.entry(Locale.SIMPLIFIED_CHINESE, "第{0}項")
                ));
    }

    public FieldNameExtractor(String fieldSeparator, int listLength, Map<Locale, String> localeListItemPlaceHolderMap) {
        this.fieldSeparator = fieldSeparator;
        this.listLength = listLength;
        this.localeListItemPlaceHolderMap = localeListItemPlaceHolderMap;
    }

    public <T> Map<String, String> getFieldNames(Class<T> clazz) {
        return getFieldNames(clazz, Locale.ENGLISH);
    }

    public <T> Map<String, String> getFieldNames(Class<T> clazz, Locale locale) {
        String parentFieldName = getClassName(clazz, locale);
        List<String> parentFieldNameList = new ArrayList<>();
        parentFieldNameList.add(parentFieldName);
        List<String> parentNodeNameList = new ArrayList<>();
        return getFieldNameRecursively(clazz, parentFieldNameList, parentNodeNameList, locale);
    }

    private <T> Map<String, String> getFieldNameRecursively(Class<T> clazz, List<String> parentFieldNameList, List<String> parentNodeNameList, Locale locale) {
        Field[] fieldArray = clazz.getDeclaredFields();
        Map<String, String> nodeNameFieldNameRootMap = new HashMap<>();
        for (Field field : fieldArray) {
            List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
            List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
            Map<String, String> nodeNameFieldNameMap = getFieldNameRecursively(field, parentFieldNameListCopy, parentNodeNameListCopy, locale);
            nodeNameFieldNameRootMap.putAll(nodeNameFieldNameMap);
        }
        List<Method> methodList = Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AssertFalse.class) || method.isAnnotationPresent(AssertTrue.class))
                .toList();
        for (Method method : methodList) {
            List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
            List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
            String methodFieldName = getMethodName(method, locale);
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

    private Map<String, String> getFieldNameRecursively(Field field, List<String> parentFieldNameList, List<String> parentNodeNameList, Locale locale) {
        if (isPrimitiveOrWrappedOrStringOrEnum(field.getType())) {
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            String leafFieldName = getFieldName(field, locale);
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
            String listFieldName = parentFieldNameList.isEmpty() ? getFieldName(field, locale) : MessageFormat.format("{0}{1}{2}", parentFieldNameListJoinName, fieldSeparator, getFieldName(field, locale));
            listRootFieldNameMap.put(listNodeName, listFieldName);
            for (int i = 0; i < listLength; i++) {
                List<String> parentNodeNameListCopy = new ArrayList<>(parentNodeNameList);
                List<String> parentFieldNameListCopy = new ArrayList<>(parentFieldNameList);
                String turnParentNodeName = MessageFormat.format("{0}[{1}]", listNodeName, i);
                String turnParentFieldName = MessageFormat.format("{0}{1}{2}", listFieldName, fieldSeparator, getListItemFieldNameByLocale(i + 1, locale));
                parentNodeNameListCopy.add(turnParentNodeName);
                parentFieldNameListCopy.add(turnParentFieldName);
                AnnotatedType annotatedType = field.getAnnotatedType();
                AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
                AnnotatedType annotatedActualTypeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
                String annotatedTypedFieldName = getFieldName(annotatedActualTypeArgument, locale);
                parentFieldNameListCopy.add(annotatedTypedFieldName);
                Map<String, String> listTurnFieldNameMap = getFieldNameRecursively(clazz, parentFieldNameListCopy, parentNodeNameListCopy, locale);
                listRootFieldNameMap.putAll(listTurnFieldNameMap);
            }

            return listRootFieldNameMap;
        } else {
            String leafFieldName = getFieldName(field, locale);
            parentFieldNameList.add(leafFieldName);
            String leafNodeName = getNodeName(field);
            parentNodeNameList.add(leafNodeName);
            return getFieldNameRecursively(field.getType(), parentFieldNameList, parentNodeNameList, locale);
        }
    }

    private String getFieldName(Field field, Locale locale) {
        return tryToGetFieldNameFromAnnotationInSpecificLocale(field, locale)
                .orElse(tryToGetFieldNameFromAnnotationInSpecificLocale(
                        field.getType(), locale
                        )
                        .orElse(
                                getNodeName(field)
                        )
                );
    }

    private <T> String getClassName(Class<T> clazz, Locale locale) {
        return tryToGetFieldNameFromAnnotationInSpecificLocale(clazz, locale)
                .orElse(getNodeName(clazz));
    }

    private <T> String getNodeName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    private String getMethodName(Method method, Locale locale) {
        return tryToGetFieldNameFromAnnotationInSpecificLocale(method, locale)
                .orElse(getNodeName(method));
    }

    private String getNodeName(Method method) {
        String methodName = method.getName();
        return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
    }

    private String getNodeName(Field field) {
        return field.getName();
    }

    private String getFieldName(AnnotatedType annotatedType, Locale locale) {
        Optional<String> optionalFieldNameFromAnnotation = tryToGetFieldNameFromAnnotationInSpecificLocale(annotatedType, locale);
        return optionalFieldNameFromAnnotation.orElse("Item");
    }

    private Optional<String> tryToGetFieldNameFromAnnotationInSpecificLocale(AnnotatedElement annotatedElement, Locale locale) {
        FieldName[] fieldNameAnnotationArray = annotatedElement.getAnnotationsByType(FieldName.class);
        if (fieldNameAnnotationArray.length == 0) {
            return Optional.empty();
        }
        var streamFilteredByLocale = stream(fieldNameAnnotationArray).filter(x -> x.locale().equals(LocaleConstant.fromLocale(locale)));
        if (streamFilteredByLocale.findAny().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                String.join(
                        fieldSeparator,
                        stream(fieldNameAnnotationArray).filter(x -> x.locale().equals(LocaleConstant.fromLocale(locale))).map(FieldName::value).toList()
                )
        );
    }

    private String getListItemFieldNameByLocale(int number, Locale locale) {
        if (localeListItemPlaceHolderMap.containsKey(locale)) {
            String placeHolder = localeListItemPlaceHolderMap.get(locale);
            return MessageFormat.format(placeHolder, number);
        }
        return MessageFormat.format(defaultListItemPlaceHolder, number);
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
