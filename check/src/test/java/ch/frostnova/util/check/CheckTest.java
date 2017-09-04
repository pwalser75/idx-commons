package ch.frostnova.util.check;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.function.Consumer;

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

    public static <T> void checkOk(T value, Consumer<? super T>... validators) {
        String parameterName = UUID.randomUUID().toString();
        T result = Check.required(value, parameterName, validators);
        Assert.assertEquals(value, result);
    }

    public static <T> void checkFail(T value, Consumer<? super T>... validators) {
        String parameterName = UUID.randomUUID().toString();
        try {
            Check.required(value, parameterName, validators);
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            System.out.println(value + " => " + ex.getMessage());
            Assert.assertTrue(ex.getMessage().contains("'" + parameterName + "'"));
        }
    }
}
