package org.zerobs.polla.configurations;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static final Set<Locale> SUPPORTED_LOCALES = new HashSet<>(Arrays.asList(DEFAULT_LOCALE, Locale.FRANCE));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String languageTag = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (StringUtils.isEmpty(languageTag))
            return getDefaultLocale();
        var locale = Locale.forLanguageTag(languageTag);
        if (!SUPPORTED_LOCALES.contains(locale))
            throw new RuntimeException("unsupported locale " + languageTag + ". Supported locales are: " +
                    SUPPORTED_LOCALES.stream().map(Locale::toLanguageTag).collect(Collectors.toList()));
        return locale;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var localeResolver = new CustomAcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(DEFAULT_LOCALE);
        Locale.setDefault(DEFAULT_LOCALE);
        return localeResolver;
    }
}