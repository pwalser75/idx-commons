package ch.frostnova.util.check;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Check class for <b>input validation</b> (ideal for <b>method parameters</b> or other externally provided values).
 * <p>
 * Checks can be performed on:
 * <ul>
 * <li><b>optional</b> values (value can be null, checks only executed when it's not null)</li>
 * <li><b>required</b> values (value checked to not to be null first, remaining checks only executed when it's not null)</li>
 * </ul>
 * <p>
 * Failing checks result in an <code>IllegalArgumentException</code>, with a meaningful message which check has failed, including the parameter name.<br>
 * Example: <pre>java.lang.IllegalArgumentException: 'customer id' must have at least 6 characters</pre>
 * <br>
 * Example usage:
 * <ul>
 * <li>Check that the name is not null, not blank and has no more than 20 characters:<br>
 * <code>Check.required(name, "name", CheckString.notBlank(), CheckString.max(20));</code>
 * </li>
 * <li>Check that an optional value is a finite number (not NaN or +/-Infinite) and &gt; 0:<br>
 * <code>Check.optional(val, "value", CheckNumber.finite(), CheckNumber.greaterThan(0));</code>
 * </li>
 * <li>Check that a collection is not empty, and does not contain null-Elements:<br>
 * <code>Check.required(items, "items", CheckCollection.notEmpty(), CheckCollection.noNullElements());</code>
 * </li>
 * <li>Check that a String token has an expected format (alphanumeric-uppercase and exactly 32 characters):<br>
 * <code>Check.required(token, "token", CheckString.format("[A-Z0-9]{32}", "alphanumeric uppercase"));</code>
 * </li>
 * </ul>
 * <br>
 * <b>Custom checks</b> can be created by checking with:
 * <ul>
 * <li>any <code>Verifier&lt;T&gt;</code> (check the value and throw any Exception when the check fails)</li>
 * <li>a <code>Predicate&lt;T&gt;</code> and a message (or message supplier)</li>
 * </ul>
 * <p>
 * Examples for custom validation:
 * <ul>
 * <li>Verifier check: a (zoned) date must be in the future<br>
 * <pre><code>   Verifier&lt;ZonedDateTime&gt; futureDate = d -&gt; {
 *     if (!d.isAfter(ZonedDateTime.now())) {
 *         throw new IllegalArgumentException("must be in the future");
 *     }
 * };
 * Check.required(date, "execution date", futureDate);</code></pre></li>
 * <li>Predicate check: check that a password contains lower/uppercase characters and digits, and is at lest 6 characters:<br>
 * <code>Predicate&lt;String&gt; hasUppercaseLetters = s -&gt; s.chars().anyMatch(Character::isUpperCase);<br>
 * Predicate&lt;String&gt; hasLowercaseLetters = s -&gt; s.chars().anyMatch(Character::isLowerCase);<br>
 * Predicate&lt;String&gt; hasDigits = s -&gt; s.chars().anyMatch(Character::isDigit);<br>
 * Predicate&lt;String&gt; passwordRule = hasUppercaseLetters.and(hasLowercaseLetters.and(hasDigits));<br>
 * String message = "must contain lower/uppercase characters and digits";<br>
 * <br>
 * Check.required(pwd, "password", Check.that(passwordRule, message), CheckString.min(6));</code>
 * </li>
 * </ul>
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class Check {

    private Check() {
        // static access only
    }

    /**
     * Check the given predicate on the given value. If the predicate evaluates to <code>false</code>,
     * produce am {@link IllegalArgumentException} with the message from the given error message producer.
     *
     * @param predicate            predicate to check, required
     * @param errorMessageProducer error message producer, required
     * @param <T>                  generic value type
     * @return verifier
     */
    public static <T> Verifier<T> that(Predicate<? super T> predicate, Function<T, String> errorMessageProducer) {
        Check.required(predicate, "predicate");
        Check.required(errorMessageProducer, "error message producer");
        return value -> {
            if (!predicate.test(value)) {
                throw new IllegalArgumentException(errorMessageProducer.apply(value));
            }
        };
    }

    /**
     * Check the given predicate on the given value. If the predicate evaluates to <code>false</code>,
     * produce am {@link IllegalArgumentException} with the given error message.
     *
     * @param predicate    predicate to check, required
     * @param errorMessage error message, required
     * @param <T>          generic value type
     * @return verifier
     */
    public static <T> Verifier<T> that(Predicate<? super T> predicate, String errorMessage) {
        return that(predicate, value -> errorMessage);
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
    public static <T> T optional(T value, String parameterName, Verifier<? super T>... verifiers) {
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
    public static <T> T required(T value, String parameterName, Verifier<? super T>... verifiers) {
        if (value == null) {
            throw new IllegalArgumentException(name(parameterName) + " is required");
        }
        if (verifiers != null) {
            for (Verifier<? super T> verifier : verifiers) {
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
