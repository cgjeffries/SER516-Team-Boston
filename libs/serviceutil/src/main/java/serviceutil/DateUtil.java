package serviceutil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static LocalDate toLocal(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isSameDay(LocalDate a, LocalDate b) {
        return a.getDayOfYear() == b.getDayOfYear() && a.getYear() == b.getYear();
    }
}
