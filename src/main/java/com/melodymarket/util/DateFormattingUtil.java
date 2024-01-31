package com.melodymarket.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormattingUtil {
    public static String dataFormatter(String originalDate, String originalFormat, String changeFormat){
        DateTimeFormatter originalDateTimeFormatter = DateTimeFormatter.ofPattern(originalFormat);
        DateTimeFormatter changeDateTimeFormatter = DateTimeFormatter.ofPattern(changeFormat);
        LocalDate date = LocalDate.parse(originalDate, originalDateTimeFormatter);

        return date.format(changeDateTimeFormatter);
    }
}
