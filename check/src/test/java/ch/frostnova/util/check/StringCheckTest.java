package ch.frostnova.util.check;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Test for {@link StringCheck}s
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class StringCheckTest {

    private final Pattern format = Pattern.compile("\\w+-[0-9]+");

    @Test
    public void checkNotEmptyOk() {
        Check.required(" aloha ", "test", StringCheck.notEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNotEmptyTrivialString() {
        Check.required("", "test", StringCheck.notEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNotEmptyWhitespaceOnly() {
        Check.required(" \t\n\r", "test", StringCheck.notEmpty());
    }

    @Test
    public void checkFormatOk() {
        Check.required("Test-123", "test", StringCheck.format(format));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkFormatWrong() {
        Check.required("Foo", "test", StringCheck.format(format));
    }
}
