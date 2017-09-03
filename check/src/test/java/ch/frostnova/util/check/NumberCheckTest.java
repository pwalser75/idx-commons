package ch.frostnova.util.check;

import org.junit.Test;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Test for {@link StringCheck}s
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class NumberCheckTest {

    private final Pattern format = Pattern.compile("\\w+-[0-9]+");

    @Test
    public void checkMinOk() {
        Check.required(5.32, "test", NumberCheck.min(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMinFail() {
        Check.required(BigInteger.valueOf(-99), "test", NumberCheck.min(5));
    }

    @Test
    public void checkMaxOk() {
        Check.required(5.32, "test", NumberCheck.max(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMaxFail() {
        Check.required(1234, "test", NumberCheck.max(5));
    }

    @Test
    public void checkMinMaxOk() {
        Check.required(123, "test", NumberCheck.min(10), NumberCheck.max(500));
        Check.required(10, "test", NumberCheck.min(10), NumberCheck.max(500));
        Check.required(500, "test", NumberCheck.min(10), NumberCheck.max(500));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMinMaxFailTooSmall() {
        Check.required(9, "test", NumberCheck.min(10), NumberCheck.max(500));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMinMaxFailTooLarge() {
        Check.required(501, "test", NumberCheck.min(10), NumberCheck.max(500));
    }
}
