package taiga.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaigaUtil {
    /**
     * Extract a slug from a given string
     *
     * @param value The string to extract the slug from
     * @return The extracted slug if found, otherwise null
     */
    public static String extractSlug(String value) {
        if (value == null) {
            return null;
        }
        Pattern urlPattern =
                Pattern.compile("https://tree\\.taiga\\.io/project/(?<slug>\\w+-[\\w-]+)");
        Matcher urlMatcher = urlPattern.matcher(value);

        Pattern slugPattern = Pattern.compile("(?<slug>\\w+-[\\w-]+)");
        Matcher slugMatcher = slugPattern.matcher(value);

        if (urlMatcher.find()) {
            return urlMatcher.group("slug");
        } else if (slugMatcher.find()) {
            return slugMatcher.group("slug");
        }
        return null;
    }
}
