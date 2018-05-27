package ch.frostnova.util.check;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Test for {@link Check} util
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckTest {

    @Test
    public void checkErrorMessageContainsParameterName() {

        String parameterName = UUID.randomUUID().toString();
        try {
            Check.required(null, parameterName);
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains(parameterName));
        }
        try {
            Check.required(null, null);
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("value"));
        }
    }

    @Test
    public void checkRequiredValuePresent() {
        Integer value = Check.required(123, "test");
        Assert.assertEquals(Integer.valueOf(123), value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkRequiredValueMissing() {
        Check.required(null, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void customCheckWithConsumer() {

        ZonedDateTime date = ZonedDateTime.now().minusWeeks(1);

        Verify<ZonedDateTime> futureDate = d -> {
            if (!d.isAfter(ZonedDateTime.now())) {
                throw new IllegalArgumentException("must be in the future");
            }
        };

        Check.required(date, "execution date", futureDate);
    }

    @Test
    public void checkOptionalValuePresent() {
        Integer value = Check.optional(123, "test");
        Assert.assertEquals(Integer.valueOf(123), value);
    }

    @Test
    public void checkOptionalValueMissing() {
        Integer value = Check.optional(null, "test");
        Assert.assertNull(value);
    }

    public static <T> void checkOk(T value, Verify<? super T>... validators) {
        String parameterName = "Test value: " + value;
        T result = Check.required(value, parameterName, validators);
        Assert.assertEquals(value, result);

    }

    public static <T> void checkFail(T value, Verify<? super T>... validators) {
        String parameterName = "Test value: " + value;
        try {
            Check.required(value, parameterName, validators);
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("'" + parameterName + "'"));
        }
    }
}
