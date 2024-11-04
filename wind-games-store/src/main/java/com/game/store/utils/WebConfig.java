package com.game.store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter(formatter));
    }

    private static class LocalDateFormatter implements Formatter<LocalDate> {
        private final DateTimeFormatter formatter;

        public LocalDateFormatter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return LocalDate.parse(text, formatter);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return formatter.format(object);
        }
    }
}

