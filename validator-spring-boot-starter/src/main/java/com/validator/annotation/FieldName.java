package com.validator.annotation;

import com.validator.constant.LocaleConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE_USE})
@Repeatable(FieldName.FieldNameList.class)
public @interface FieldName {
    String value() default "";
    String locale() default LocaleConstant.ENGLISH;


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE_USE})
    @interface FieldNameList {
        FieldName[] value();
    }
}
