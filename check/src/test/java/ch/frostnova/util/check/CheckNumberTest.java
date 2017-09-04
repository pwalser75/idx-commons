package ch.frostnova.util.check;

import org.junit.Test;

/**
 * Test for {@link CheckString}s
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckNumberTest {

    @Test
    public void checkMin() {
        CheckTest.checkOk(5d, CheckNumber.min(5));
        CheckTest.checkOk(5.32, CheckNumber.min(5));
        CheckTest.checkFail(-20, CheckNumber.min(-15));
    }

    @Test
    public void checkMax() {
        CheckTest.checkOk(5d, CheckNumber.max(5));
        CheckTest.checkOk(5.32, CheckNumber.max(10));
        CheckTest.checkFail(-99, CheckNumber.max(-100));
    }

    @Test
    public void checkLessThan() {
        CheckTest.checkFail(5d, CheckNumber.lessThan(5));

        CheckTest.checkOk(5.32, CheckNumber.lessThan(5));
        CheckTest.checkFail(-20, CheckNumber.lessThan(-15));
    }

    @Test
    public void checkGreaterThan() {
        CheckTest.checkFail(5d, CheckNumber.greaterThan(5));

        CheckTest.checkOk(199, CheckNumber.greaterThan(10));
        CheckTest.checkFail(-99, CheckNumber.greaterThan(-5));
    }
}
