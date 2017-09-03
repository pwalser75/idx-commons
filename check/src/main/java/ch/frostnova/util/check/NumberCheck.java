package ch.frostnova.util.check;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Utility methods to perform checks on numbers
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public final class NumberCheck {

    private NumberCheck() {
        // static access only
    }

    public static Consumer<Number> min(Number min) {
        return value -> {
            if (min != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(min.toString())) < 0) {
                throw new IllegalArgumentException("must not be smaller than " + min);
            }
        };
    }

    public static Consumer<Number> max(Number max) {
        return value -> {
            if (max != null && new BigDecimal(value.toString()).compareTo(new BigDecimal(max.toString())) > 0) {
                throw new IllegalArgumentException("must not be larger than " + max);
            }
        };
    }
}