package ch.frostnova.util.check;

/**
 * Verifier interface, for check to be performed with {@link Check}.
 *
 * @author pwalser
 * @since 26.05.2018.
 */
@FunctionalInterface
public interface Verifier<T> {

    /**
     * Check the value. Throw any exception (with a message) when
     * the value does not match the criteria of this verifier.
     *
     * @param value value to check, never null
     */
    void check(T value) throws Exception;
}
