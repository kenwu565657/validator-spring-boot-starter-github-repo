package com.validator.annotation;

import java.util.Locale;

public class LocaleConstant {
    public static final String ENGLISH = "ENGLISH";
    public static final String TRADITIONAL_CHINESE = "TRADITIONAL_CHINESE";
    public static final String SIMPLIFIED_CHINESE = "SIMPLIFIED_CHINESE";

    public static Locale toLocale(String localeConstant) {
        return switch (localeConstant) {
            case ENGLISH -> Locale.ENGLISH;
            case TRADITIONAL_CHINESE -> Locale.TRADITIONAL_CHINESE;
            case SIMPLIFIED_CHINESE -> Locale.SIMPLIFIED_CHINESE;
            default -> Locale.ENGLISH;
        };
    }

    public static String fromLocale(Locale locale) {
        if (locale == Locale.ENGLISH) {
            return ENGLISH;
        }
        if (locale == Locale.TRADITIONAL_CHINESE) {
            return TRADITIONAL_CHINESE;
        }
        if (locale == Locale.SIMPLIFIED_CHINESE) {
            return SIMPLIFIED_CHINESE;
        }
        return ENGLISH;
    }
}
