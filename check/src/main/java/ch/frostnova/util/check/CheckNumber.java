package ch.frostnova.util.check;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Utility methods to perform checks on numbers
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class CheckNumber {

    private CheckNumber() {
        // static access only
    }

    /**
     * Check that the number is not smaller than 'min'
     *
     * @param min minimum
     * @return validator
     */
    public static Consumer<Number> min(Number min) {
        return value -> {
            if (min != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(min.toString())) < 0) {
                throw new IllegalArgumentException("must not be smaller than " + min);
            }
        };
    }

    /**
     * Check that the number is not larger than 'max'
     *
     * @param max maximum
     * @return validator
     */
    public static Consumer<Number> max(Number max) {
        return value -> {
            if (max != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(max.toString())) > 0) {
                throw new IllegalArgumentException("must not be larger than " + max);
            }
        };
    }

    /**
     * Check that the number less than a given value
     *
     * @param n value to compare against
     * @return validator
     */
    public static Consumer<Number> lessThan(Number n) {
        return value -> {
            if (n != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(n.toString())) <= 0) {
                throw new IllegalArgumentException("must be less than " + n);
            }
        };
    }

    /**
     * Check that the number greater than a given value
     *
     * @param n value to compare against
     * @return validator
     */
    public static Consumer<Number> greaterThan(Number n) {
        return value -> {
            if (n != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(n.toString())) <= 0) {
                throw new IllegalArgumentException("must be greater than " + n);
            }
        };
    }
}