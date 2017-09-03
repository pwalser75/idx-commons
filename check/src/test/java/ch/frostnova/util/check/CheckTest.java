package ch.frostnova.util.check;

import org.junit.Assert;
import org.junit.Test;

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
}
