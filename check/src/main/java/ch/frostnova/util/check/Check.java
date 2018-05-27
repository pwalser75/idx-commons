package ch.frostnova.util.check;

/**
 * Check class for <b>input validation</b>. See package info Javadoc for details and usage.
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class Check {

    private Check() {
        // static access only
    }

    /**
     * Perform checks on an optional value. If the value is null, it is accepted (as being optional); only when the
     * value is present, the verifiers are called for further verification. If any of the validations fail (by
     * throwing any exception), an {@link IllegalArgumentException} will be thrown, including the parameter name an
     * error message.
     *
     * @param value         value to be checked
     * @param parameterName parameter name of the value (will be included in the error message)
     * @param verifiers     optional verifiers which perform checks on the value (only called when the value is not
     *                      null), and throw any {@link Exception} (will be converted to an {@link
     *                      IllegalArgumentException}
     *                      is the same message) when the validation fails.
     * @param <T>           value type
     * @return value that was passed.
     */
    @SafeVarargs
    public static <T> T optional(T value, String parameterName, Verify<? super T>... verifiers) {
        return value == null ? null : required(value, parameterName, verifiers);
    }

    /**
     * Perform checks on a required value (must not be null). The verifiers are called for further verification, if the
     * value is not already null. If any of the validations fail (by
     * throwing any exception), an {@link IllegalArgumentException} will be thrown, including the parameter name an
     * error message.
     *
     * @param value         value to be checked
     * @param parameterName parameter name of the value (will be included in the error message)
     * @param verifiers     optional verifiers which perform checks on the value (only called when the value is not
     *                      null), and throw any {@link Exception} (will be converted to an {@link
     *                      IllegalArgumentException}
     *                      is the same message) when the validation fails.
     * @param <T>           value type
     * @return value that was passed.
     */
    @SafeVarargs
    public static <T> T required(T value, String parameterName, Verify<? super T>... verifiers) {
        if (value == null) {
            throw new IllegalArgumentException(name(parameterName) + " is required");
        }
        if (verifiers != null) {
            for (Verify<? super T> verifier : verifiers) {
                try {
                    verifier.check(value);
                } catch (Exception ex) {
                    String message = ex.getMessage() != null ? ex.getMessage() : "unknown reason";
                    throw new IllegalArgumentException(name(parameterName) + " " + message, ex);
                }
            }
        }
        return value;
    }

    private static String name(String parameterName) {
        return parameterName != null ? "'" + parameterName + "'" : "value";
    }
}
