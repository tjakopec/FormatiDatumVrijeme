package jakopec.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by tjakopec on 19.04.2017..
 */


//https://gist.github.com/mraccola/702330625fad8eebe7d3
public final class DateUtils {


    // ISO 8601 constants
    private static final String ISO_8601_PATTERN_1 = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String ISO_8601_PATTERN_2 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String[] SUPPORTED_ISO_8601_PATTERNS = new String[]{ISO_8601_PATTERN_1,
            ISO_8601_PATTERN_2};
    private static final int TICK_MARK_COUNT = 2;
    private static final int COLON_PREFIX_COUNT = "+00".length();
    private static final int COLON_INDEX = 22;




    /**
     * Parses a date from the specified ISO 8601-compliant string.
     *
     * @param string the string to parse
     * @return the {@link Date} resulting from the parsing, or null if the string could not be
     * parsed
     */
    public static Date parseIso8601DateTime(String string) {
        if (string == null) {
            return null;
        }
        String s = string.replace("Z", "+00:00");
        for (String pattern : SUPPORTED_ISO_8601_PATTERNS) {
            String str = s;
            int colonPosition = pattern.lastIndexOf('Z') - TICK_MARK_COUNT + COLON_PREFIX_COUNT;
            if (str.length() > colonPosition) {
                str = str.substring(0, colonPosition) + str.substring(colonPosition + 1);
            }
            try {
                return new SimpleDateFormat(pattern, Locale.US).parse(str);
            } catch (final ParseException e) {
                // try the next one
            }
        }
        return null;
    }

    /**
     * Formats the specified date to an ISO 8601-compliant string.
     *
     * @param date     the date to format
     * @param timeZone the {@link TimeZone} to use when formatting the date
     * @return the formatted string
     */
    public static String formatIso8601DateTime(Date date, TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_PATTERN_1, Locale.US);
        if (timeZone != null) {
            dateFormat.setTimeZone(timeZone);
        }
        String formatted = dateFormat.format(date);
        if (formatted != null && formatted.length() > COLON_INDEX) {
            formatted = formatted.substring(0, 22) + ":" + formatted.substring(22);
        }
        return formatted;
    }

    private DateUtils() {
        // hide constructor
    }
}