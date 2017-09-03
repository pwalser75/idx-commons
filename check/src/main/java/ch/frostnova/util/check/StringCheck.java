package ch.frostnova.util.check;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Utility methods to perform checks on strings
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class StringCheck {

    private StringCheck() {
        // static access only
    }

    public static Consumer<String> notEmpty() {

        return value -> {
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("must not be empty");
            }
        };
    }

    public static Consumer<String> format(Pattern pattern) {
        return value -> {
            if (pattern != null && !pattern.matcher(value).matches()) {
                throw new IllegalArgumentException("invalid format");
            }
        };
    }
}