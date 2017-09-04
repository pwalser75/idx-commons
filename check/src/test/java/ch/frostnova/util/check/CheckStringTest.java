package ch.frostnova.util.check;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Test for {@link CheckString}
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckStringTest {

    @Test
    public void checkNotEmpty() {
        CheckTest.checkOk("Test", CheckString.notEmpty());
        CheckTest.checkOk("  ", CheckString.notEmpty());
        CheckTest.checkOk("\t", CheckString.notEmpty());
        CheckTest.checkFail("", CheckString.notEmpty());
    }

    @Test
    public void checkFormat() {
        final Pattern format = Pattern.compile("\\w+-[0-9]+");
        CheckTest.checkOk("Test-123", CheckString.format(format));
        CheckTest.checkOk("K-2", CheckString.format(format));
        CheckTest.checkFail("123-Test", CheckString.format(format));
        CheckTest.checkFail("Foo", CheckString.format(format));
        CheckTest.checkFail("x=123", CheckString.format(format));
    }

    @Test
    public void checkFormatWithHint() {
        final Pattern format = Pattern.compile("[0-9]{4}-[0-1][0-9]?-[0-3][0-9]?");
        String hint = "expected: yyyy-MM-dd";

        CheckTest.checkOk("1975-12-20", CheckString.format(format, hint));
        CheckTest.checkOk("2017-09-04", CheckString.format(format, hint));
        CheckTest.checkFail("5555", CheckString.format(format, hint));
        CheckTest.checkFail("Foo", CheckString.format(format, hint));
        CheckTest.checkFail("2000-47-99", CheckString.format(format, hint));
    }

    @Test
    public void checkNotBlank() {
        CheckTest.checkOk("?", CheckString.notBlank());
        CheckTest.checkOk("Test", CheckString.notBlank());
        CheckTest.checkFail("", CheckString.notBlank());
        CheckTest.checkFail("  ", CheckString.notBlank());
        CheckTest.checkFail(" \t\r\n", CheckString.notBlank());
    }

    @Test
    public void checkMin() {
        CheckTest.checkOk("123", CheckString.min(3));
        CheckTest.checkOk("Aloha", CheckString.min(3));
        CheckTest.checkFail("X", CheckString.min(3));
        CheckTest.checkFail("", CheckString.min(3));
    }

    @Test
    public void checkMax() {
        CheckTest.checkOk("123", CheckString.max(3));
        CheckTest.checkOk("X", CheckString.max(3));
        CheckTest.checkOk("", CheckString.max(3));
        CheckTest.checkFail("Aloha", CheckString.max(3));
    }
}
