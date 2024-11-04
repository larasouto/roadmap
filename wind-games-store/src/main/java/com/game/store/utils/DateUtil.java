package com.game.store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_PATTERN);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Formato esperado: dd-MM-yyyy");
        }
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_PATTERN.format(date);
    }
}
