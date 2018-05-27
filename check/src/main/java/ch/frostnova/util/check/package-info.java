

/**
 * The <b>Frostnova Check Framework</b> offers a {@link ch.frostnova.util.check.Check} class for
 * <b>parameter/value validation</b> using a rich and expandable set of utility functions.
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
 * <li>Check that a value (here: parameter 'url' named 'connection url') is present (not null):<br>
 * <code>Check.required(url, "connection url");</code>
 * </li>
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
 * <b>Custom checks</b> can be created with any implementation of the
 * {@link ch.frostnova.util.check.Verify} functional interface. This interface also provides static methods
 * to create implementations based on {@link java.util.function.Predicate} and predefined or dynamic
 * failure messages.
 * <p>
 * Examples for custom validation:
 * <ul>
 * <li>Verify that a (zoned) date must be in the future<br>
 * <pre><code>   Verify&lt;ZonedDateTime&gt; futureDate = d -&gt; {
 *     if (!d.isAfter(ZonedDateTime.now())) {
 *         throw new IllegalArgumentException("must be in the future");
 *     }
 * };
 * Check.required(date, "execution date", futureDate);</code></pre>
 * or <pre><code>Check.required(date, "execution date",
 *   Verify.that(d -&gt; !d.isAfter(ZonedDateTime.now()), "must be in the future"));</code></pre>
 * </ul>
 *
 * @author pwalser
 * @since 27.05.2018.
 */
package ch.frostnova.util.check;