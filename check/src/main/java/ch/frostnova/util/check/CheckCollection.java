package ch.frostnova.util.check;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility methods to perform checks on {@link Collection} values.
 *
 * @author pwalser
 * @since 04.09.2017.
 */
public final class CheckCollection {

    private CheckCollection() {
        // static access only
    }

    /**
     * Check that the collection is not empty.
     *
     * @return verifier
     */
    public static Verify<Collection> notEmpty() {
        return Verify.that(c -> !c.isEmpty(), "must not be empty");
    }

    /**
     * Check that the collection does not contain any null elements
     *
     * @return verifier
     */
    public static Verify<Collection> noNullElements() {
        return Verify.that(c -> c.stream().noneMatch(Objects::isNull), "must not be empty");
    }

    /**
     * Check that the collection contains a minimum number of elements
     *
     * @param min minimum
     * @return verifier
     */
    public static Verify<Collection> min(int min) {
        return Verify.that(v -> v.size() >= min, "must have at least " + min + " elements");
    }

    /**
     * Check that the collection contains a maximum number of elements
     *
     * @param max maximum
     * @return verifier
     */
    public static Verify<Collection> max(int max) {
        return Verify.that(v -> v.size() <= max, "must have no more than " + max + " elements");
    }
}