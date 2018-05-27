package ch.frostnova.util.check;

import org.junit.Test;

import java.util.regex.Pattern;

import static ch.frostnova.util.check.CheckString.*;

/**
 * Test for {@link CheckString}
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckStringTest {

    @Test
    public void checkNotEmpty() {
        CheckTest.checkOk("Test", notEmpty());
        CheckTest.checkOk("  ", notEmpty());
        CheckTest.checkOk("\t", notEmpty());
        CheckTest.checkFail("", notEmpty());
    }

    @Test
    public void checkFormat() {
        final Pattern format = Pattern.compile("\\w+-[0-9]+");
        CheckTest.checkOk("Test-123", format(format));
        CheckTest.checkOk("K-2", format(format));
        CheckTest.checkFail("123-Test", format(format));
        CheckTest.checkFail("Foo", format(format));
        CheckTest.checkFail("x=123", format(format));
    }

    @Test
    public void checkStringFormat() {
        final String format = "\\w+-[0-9]+";
        CheckTest.checkOk("Test-123", format(format));
        CheckTest.checkOk("K-2", format(format));
        CheckTest.checkFail("123-Test", format(format));
        CheckTest.checkFail("Foo", format(format));
        CheckTest.checkFail("x=123", format(format));
    }

    @Test
    public void checkFormatWithHint() {
        final Pattern format = Pattern.compile("[0-9]{4}-[0-1][0-9]?-[0-3][0-9]?");
        String hint = "expected: yyyy-MM-dd";

        CheckTest.checkOk("1975-12-20", format(format, hint));
        CheckTest.checkOk("2017-09-04", format(format, hint));
        CheckTest.checkFail("5555", format(format, hint));
        CheckTest.checkFail("Foo", format(format, hint));
        CheckTest.checkFail("2000-47-99", format(format, hint));
    }

    @Test
    public void checkStringFormatWithHint() {
        final String format = "[0-9]{4}-[0-1][0-9]?-[0-3][0-9]?";
        String hint = "expected: yyyy-MM-dd";

        CheckTest.checkOk("1975-12-20", format(format, hint));
        CheckTest.checkOk("2017-09-04", format(format, hint));
        CheckTest.checkFail("5555", format(format, hint));
        CheckTest.checkFail("Foo", format(format, hint));
        CheckTest.checkFail("2000-47-99", format(format, hint));
    }

    @Test
    public void checkNotBlank() {
        CheckTest.checkOk("?", notBlank());
        CheckTest.checkOk("Test", notBlank());
        CheckTest.checkFail("", notBlank());
        CheckTest.checkFail("  ", notBlank());
        CheckTest.checkFail(" \t\r\n", notBlank());
    }

    @Test
    public void checkMin() {
        CheckTest.checkOk("123", min(3));
        CheckTest.checkOk("Aloha", min(3));
        CheckTest.checkFail("X", min(3));
        CheckTest.checkFail("", min(3));
    }

    @Test
    public void checkMax() {
        CheckTest.checkOk("123", max(3));
        CheckTest.checkOk("X", max(3));
        CheckTest.checkOk("", max(3));
        CheckTest.checkFail("Aloha", max(3));
    }

    @Test
    public void checkSingleWord() {
        CheckTest.checkOk("a", singleWord());
        CheckTest.checkOk("abc", singleWord());
        CheckTest.checkOk("Kräuterwürze", singleWord());
        CheckTest.checkOk("Désenchantée", singleWord());

        CheckTest.checkFail("", singleWord());
        CheckTest.checkFail("#@!&", singleWord());
        CheckTest.checkFail("123", singleWord());
        CheckTest.checkFail("42foo", singleWord());
        CheckTest.checkFail("two words", singleWord());
        CheckTest.checkFail("what?", singleWord());
        CheckTest.checkFail(" spaces ", singleWord());
    }
}
