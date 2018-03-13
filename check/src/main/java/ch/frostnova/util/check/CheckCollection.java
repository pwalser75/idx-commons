package ch.frostnova.util.check;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Utility methods to perform checks on collections
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
     * @return validator
     */
    public static Consumer<Collection> notEmpty() {
        return Check.with(c -> !c.isEmpty(), "must not be empty");
    }

    /**
     * Check that the collection does not contain any null elements
     *
     * @return validator
     */
    public static Consumer<Collection> noNullElements() {
        return Check.with(c -> !c.stream().anyMatch(Objects::isNull), "must not be empty");
    }

    /**
     * Check that the collection contains a minimum number of elements
     *
     * @param min minimum
     * @return validator
     */
    public static Consumer<Collection> min(int min) {
        return Check.with(v -> v.size() >= min, "must have at least " + min + " elements");
    }

    /**
     * Check that the collection contains a maximum number of elements
     *
     * @param max maximum
     * @return validator
     */
    public static Consumer<Collection> max(int max) {
        return Check.with(v -> v.size() <= max, "must have no more than " + max + " elements");
    }
}