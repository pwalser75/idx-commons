package ch.frostnova.util.check;

import java.util.regex.Pattern;

/**
 * Utility methods to perform checks on {@link String} values.
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class CheckString {

    private CheckString() {
        // static access only
    }


    /**
     * Check that the string is not empty (string is no characters)
     *
     * @return verifier
     */
    public static Verifier<String> notEmpty() {

        return value -> {
            if (value.length() == 0) {
                throw new IllegalArgumentException("must not be empty");
            }
        };
    }

    /**
     * Check that the string is not empty (string is whitespace characters only)
     *
     * @return verifier
     */
    public static Verifier<String> notBlank() {

        return value -> {
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("must not be empty");
            }
        };
    }

    /**
     * Check that the string's format matches the given pattern
     *
     * @param pattern pattern specifying the string's format
     * @return verifier
     */
    public static Verifier<String> format(String pattern) {
        return format(Pattern.compile(pattern));
    }

    /**
     * Check that the string's format matches the given pattern
     *
     * @param pattern pattern specifying the string's format
     * @return verifier
     */
    public static Verifier<String> format(Pattern pattern) {
        return format(pattern, null);
    }

    /**
     * Check that the string's format matches the given pattern
     *
     * @param pattern pattern specifying the string's format
     * @param hint    hint describing the pattern
     * @return verifier
     */
    public static Verifier<String> format(Pattern pattern, String hint) {
        return value -> {
            if (pattern != null && !pattern.matcher(value).matches()) {
                StringBuilder builder = new StringBuilder();
                builder.append("invalid format");
                if (hint != null) {
                    builder.append(" (");
                    builder.append(hint);
                    builder.append(")");
                }
                throw new IllegalArgumentException(builder.toString());
            }
        };
    }

    /**
     * Check that the string's format matches the given pattern
     *
     * @param pattern pattern specifying the string's format
     * @param hint    hint describing the pattern
     * @return verifier
     */
    public static Verifier<String> format(String pattern, String hint) {
        return format(Pattern.compile(pattern), hint);
    }

    /**
     * Check that the string contains at least 'min' characters
     *
     * @param min minimum number of characters
     * @return verifier
     */
    public static Verifier<String> min(int min) {

        return value -> {
            if (value.length() < min) {
                throw new IllegalArgumentException("must have at least " + min + " characters");
            }
        };
    }

    /**
     * Check that the string contains no more than 'max' characters
     *
     * @param max maximum number of characters
     * @return verifier
     */
    public static Verifier<String> max(int max) {

        return value -> {
            if (value.length() > max) {
                throw new IllegalArgumentException("must have no more than " + max + " characters");
            }
        };
    }
}