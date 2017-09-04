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

        return value -> {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("must not be empty");
            }
        };
    }

    /**
     * Check that the collection does not contain any null elements
     *
     * @return validator
     */
    public static Consumer<Collection> noNullElements() {

        return value -> {
            if (value.stream().anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException("must not contain null elements");
            }
        };
    }

    /**
     * Check that the collection contains a minimum number of elements
     *
     * @param min minimum
     * @return validator
     */
    public static Consumer<Collection> min(int min) {

        return value -> {
            if (value.size() < min) {
                throw new IllegalArgumentException("must have at least " + min + " elements");
            }
        };
    }

    /**
     * Check that the collection contains a maximum number of elements
     *
     * @param max maximum
     * @return validator
     */
    public static Consumer<Collection> max(int max) {

        return value -> {
            if (value.size() > max) {
                throw new IllegalArgumentException("must have no more than " + max + " elements");
            }
        };
    }
}