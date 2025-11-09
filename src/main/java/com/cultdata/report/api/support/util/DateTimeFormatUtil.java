package com.cultdata.report.api.support.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DateTimeFormatUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);

    /*
        input  : 2025-06-27T07:49:59.162+00:00
        output : 27 Jun 2025 07:49:59 GMT
     */
    public static String formatToOutput(String dateTimeVaue) {
        if (dateTimeVaue == null || dateTimeVaue.isEmpty()) return dateTimeVaue;

        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeVaue);
            return OUTPUT_FORMATTER.format(zonedDateTime);
        } catch (Exception e) {
            // Log warning here if needed
            return dateTimeVaue;
        }
    }


    public static LocalDateTime parseStartOfDay(String date) {
        return (date != null) ? LocalDateTime.parse(date + " 00:00:00", FORMATTER) : null;
    }

    public static LocalDateTime parseEndOfDay(String date) {
        return (date != null) ? LocalDateTime.parse(date + " 23:59:59", FORMATTER) : null;
    }
}