package com.validator.core;

import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolverContext;

import java.util.Locale;

public class FixedLocaleResolver implements LocaleResolver {
    private final Locale locale;

    public FixedLocaleResolver(Locale locale) {
        this.locale = locale;
    }

    public static FixedLocaleResolver of(Locale locale) {
        return new FixedLocaleResolver(locale);
    }

    @Override
    public Locale resolve(LocaleResolverContext localeResolverContext) {
        return locale;
    }
}
