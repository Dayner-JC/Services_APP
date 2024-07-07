package dev.godjango.apk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getCurrentDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd. yyyy", Locale.ENGLISH);
        return sdf.format(new Date());
    }
}
