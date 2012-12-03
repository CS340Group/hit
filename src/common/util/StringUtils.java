package common.util;


public final class StringUtils {

    public static String capitalize(String s) {
        char[] charArray = s.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return new String(charArray);
    }
}
