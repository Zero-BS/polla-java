package org.zerobs.polla.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Configuration
public class BeansConfig {
    @Value("${locale.default}")
    private String defaultLocaleTag;
    @Value("${locale.supported}")
    private List<String> supportedLocaleTags;

    @Bean
    public LocaleResolver localeResolver() {
        var defaultLocale = Locale.forLanguageTag(defaultLocaleTag);
        var supportedLocales = supportedLocaleTags.stream().map(Locale::forLanguageTag).collect(toList());

        var localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(defaultLocale);
        Locale.setDefault(defaultLocale);
        localeResolver.setSupportedLocales(supportedLocales);
        return localeResolver;
    }
}
