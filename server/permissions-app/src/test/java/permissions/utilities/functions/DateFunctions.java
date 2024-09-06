package permissions.utilities.functions;

import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateFunctions {
    public static @NotNull Long todayDateInEpoch() {
        final String pattern = "MM/dd/yyyy HH:mm:ss";
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        final LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        final String formattedStartOfDay = startOfDay.format(dateTimeFormatter);
        return LocalDateTime.parse(formattedStartOfDay, dateTimeFormatter).toEpochSecond(ZoneOffset.UTC);
    }

    public static @NotNull String todayDate_yyyy_MM_dd() {
        final String dateFormat = "yyyy-MM-dd";
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return currentDateTime.format(formatter);
    }
}
