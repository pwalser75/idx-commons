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
     * Check that the number is an actual number (not infinity, not NaN).
     *
     * @return validator
     */
    public static Consumer<Number> finite() {
        return value -> {
            if (value instanceof Float) {
                if (!Float.isFinite((Float) value)) {
                    throw new IllegalArgumentException("is not a finite number");
                }
            } else if (value instanceof Double) {
                if (!Double.isFinite((Double) value)) {
                    throw new IllegalArgumentException("is not a finite number");
                }
            }
        };
    }

    /**
     * Check that the number is not smaller than 'min'
     *
     * @param min minimum
     * @return validator
     */
    public static Consumer<Number> min(Number min) {
        if (isNaN(min) || isNegativeInfinity(min)) {
            return value -> {
            };
        }
        if (isPositiveInfinity(min)) {
            return value -> {
                if (!isPositiveInfinity(value)) {
                    throw new IllegalArgumentException("must be " + min);
                }
            };
        }

        return value -> {
            if (min != null && !isPositiveInfinity(value) && !isNaN(value) && (isNegativeInfinity(value) || new BigDecimal(value.toString()).compareTo(new BigDecimal(min.toString())) < 0)) {
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
        if (isNaN(max) || isPositiveInfinity(max)) {
            return value -> {
            };
        }
        if (isNegativeInfinity(max)) {
            return value -> {
                if (!isNegativeInfinity(value)) {
                    throw new IllegalArgumentException("must be " + max);
                }
            };
        }

        return value -> {
            if (max != null && !isNegativeInfinity(value) && !isNaN(value) && (isPositiveInfinity(value) || new BigDecimal(value.toString()).compareTo(new BigDecimal(max.toString())) > 0)) {
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
        if (isNaN(n) || isPositiveInfinity(n)) {
            return value -> {
            };
        }
        if (isNegativeInfinity(n)) {
            return value -> {
                throw new IllegalArgumentException("must be smaller than " + n + " (which it can't be, sorry)");
            };
        }

        return value -> {
            if (n != null && !isNegativeInfinity(value) && !isNaN(value) && (isPositiveInfinity(value) || new BigDecimal(value.toString()).compareTo(new BigDecimal(n.toString())) >= 0)) {
                throw new IllegalArgumentException("must be smaller than " + n);
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
        if (isNaN(n) || isNegativeInfinity(n)) {
            return value -> {
            };
        }
        if (isPositiveInfinity(n)) {
            return value -> {
                throw new IllegalArgumentException("must be greater than " + n + " (which it can't be, sorry)");
            };
        }

        return value -> {
            if (n != null && !isPositiveInfinity(value) && !isNaN(value) && (isNegativeInfinity(value) || new BigDecimal(value.toString()).compareTo(new BigDecimal(n.toString())) <= 0)) {
                throw new IllegalArgumentException("must be greater than " + n);
            }
        };
    }

    private static boolean isPositiveInfinity(Number number) {
        return number instanceof Double && number.doubleValue() == Double.POSITIVE_INFINITY
                || number instanceof Float && number.floatValue() == Float.POSITIVE_INFINITY;
    }

    private static boolean isNegativeInfinity(Number number) {
        return number instanceof Double && number.doubleValue() == Double.NEGATIVE_INFINITY
                || number instanceof Float && number.floatValue() == Float.NEGATIVE_INFINITY;
    }

    private static boolean isNaN(Number number) {
        return number instanceof Double && Double.isNaN(number.doubleValue())
                || number instanceof Float && Float.isNaN(number.floatValue());
    }
}