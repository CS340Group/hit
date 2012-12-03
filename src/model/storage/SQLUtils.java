package model.storage;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: murphyra
 */
public class SQLUtils {
    public static DateTime DateFromLongString(String s) {
        try {
            return new DateTime(Long.parseLong(s));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String DateToLongString(DateTime d) {
        try {
            return String.valueOf(d.getMillis());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
