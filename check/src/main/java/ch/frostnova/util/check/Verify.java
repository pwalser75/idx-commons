package ch.frostnova.util.check;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Verifier interface, for check to be performed with {@link Check}.
 *
 * @author pwalser
 * @since 26.05.2018.
 */
@FunctionalInterface
public interface Verify<T> {

    /**
     * Check the value. Throw any exception (with a message) when
     * the value does not match the criteria of this verifier.
     *
     * @param value value to check, never null
     */
    void check(T value) throws Exception;

    /**
     * Create a verifier based on a predicate and message. Upon checking, the predicate
     * will be evaluated, and if it evaluates to 'false' or throws an Exception, throws
     * an {@link IllegalArgumentException} with the given message.
     *
     * @param test    test to perform, required. Pass with 'true' and fail with 'false'.
     * @param message message to return, required.
     * @param <T>     generic value type
     * @return verifier
     */
    static <T> Verify<T> that(Predicate<? super T> test, String message) {
        return that(test, value -> message);
    }

    /**
     * Create a verifier based on a predicate and message producer. Upon checking, the predicate
     * will be evaluated, and if it evaluates to 'false' or throws an Exception, throws
     * an {@link IllegalArgumentException} with a message produced based on the checked value.
     *
     * @param test            test to perform, required. Pass with 'true' and fail with 'false'.
     * @param messageProducer message producer producing the message to return, required.
     * @param <T>             generic value type
     * @return verifier
     */
    static <T> Verify<T> that(Predicate<? super T> test, Function<? super T, String> messageProducer) {
        Check.required(test, "test");
        Check.required(messageProducer, "messageProducer");

        return value -> {
            try {
                if (!test.test(value)) {
                    throw new IllegalArgumentException(messageProducer.apply(value));
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException(messageProducer.apply(value), ex);
            }
        };
    }
}
