package com.nsi.clonebin.util;

import com.nsi.clonebin.model.enums.PasteExpiringEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL dd, yyyy");
        return dateTime.toLocalDate().format(formatter);
    }

    public static String getExpiresAtTime(LocalDateTime expiresAt) {
        if (expiresAt == null) {
            return "Never";
        }
        LocalDateTime now = LocalDateTime.now();
        long diff;
        diff = ChronoUnit.YEARS.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Year");
        }

        diff = ChronoUnit.MONTHS.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Month");
        }

        diff = ChronoUnit.DAYS.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Day");
        }

        diff = ChronoUnit.HOURS.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Hour");
        }

        diff = ChronoUnit.MINUTES.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Minute");
        }

        diff = ChronoUnit.SECONDS.between(now, expiresAt);
        if (diff > 0) {
            return formatPeriod(diff, "Second");
        }

        return "Never";
    }

    public static LocalDateTime calucateExpiresAt(PasteExpiringEnum pasteExpiringEnum) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = null;
        if (pasteExpiringEnum != PasteExpiringEnum.NEVER) {
            expiresAt = now.plus(pasteExpiringEnum.getValue(), pasteExpiringEnum.getChronoUnit());
        }
        return expiresAt;
    }

    private static String formatPeriod(long diff, String period) {
        if (diff <= 1) {
            return diff + " " + period;
        } else {
            return diff + " " + period + "s";
        }
    }
}
