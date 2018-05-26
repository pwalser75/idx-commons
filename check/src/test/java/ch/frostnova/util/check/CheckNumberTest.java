package ch.frostnova.util.check;

import org.junit.Test;

import java.math.BigDecimal;

import static ch.frostnova.util.check.CheckNumber.*;

/**
 * Test for {@link CheckString}s
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckNumberTest {

    @Test
    public void foo() {

        System.out.println("a: " + Double.isInfinite(Double.POSITIVE_INFINITY));
        System.out.println("b: " + Double.isInfinite(Double.NEGATIVE_INFINITY));
        System.out.println("b: " + (Double.NEGATIVE_INFINITY == Double.NEGATIVE_INFINITY));
    }

    @Test
    public void checkFinite() {

        RandomUtil.randomNumberSuppliers().forEach(n -> CheckTest.checkOk(n.get(), finite()));

        CheckTest.checkOk(RandomUtil.randomByte(), finite());
        CheckTest.checkOk(RandomUtil.randomShort(), finite());
        CheckTest.checkOk(RandomUtil.randomInt(), finite());
        CheckTest.checkOk(RandomUtil.randomLong(), finite());
        CheckTest.checkOk(RandomUtil.randomFloat(), finite());
        CheckTest.checkOk(RandomUtil.randomDouble(), finite());
        CheckTest.checkOk(RandomUtil.randomBigInteger(), finite());
        CheckTest.checkOk(RandomUtil.randomBigDecimal(), finite());

        CheckTest.checkOk(Byte.MIN_VALUE, finite());
        CheckTest.checkOk(Byte.MAX_VALUE, finite());
        CheckTest.checkOk(Short.MIN_VALUE, finite());
        CheckTest.checkOk(Short.MAX_VALUE, finite());
        CheckTest.checkOk(Integer.MIN_VALUE, finite());
        CheckTest.checkOk(Integer.MAX_VALUE, finite());
        CheckTest.checkOk(Long.MIN_VALUE, finite());
        CheckTest.checkOk(Long.MAX_VALUE, finite());
        CheckTest.checkOk(Float.MIN_VALUE, finite());
        CheckTest.checkOk(Float.MAX_VALUE, finite());
        CheckTest.checkOk(Double.MIN_VALUE, finite());
        CheckTest.checkOk(Double.MAX_VALUE, finite());

        CheckTest.checkFail(Float.NaN, finite());
        CheckTest.checkFail(Float.POSITIVE_INFINITY, finite());
        CheckTest.checkFail(Float.NEGATIVE_INFINITY, finite());
        CheckTest.checkFail(Double.NaN, finite());
        CheckTest.checkFail(Double.POSITIVE_INFINITY, finite());
        CheckTest.checkFail(Double.NEGATIVE_INFINITY, finite());
    }

    @Test
    public void checkMin() {

        RandomUtil.randomNumberSuppliers().forEach(s -> {

            Number a = s.get();
            Number b = s.get();
            if (new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString())) > 0) {
                Number temp = a;
                a = b;
                b = temp;
            }
            CheckTest.checkOk(a, min(a));
            CheckTest.checkOk(b, min(a));

            Number n = s.get();
            CheckTest.checkOk(n, min(Float.NEGATIVE_INFINITY));
            CheckTest.checkOk(n, min(Float.NaN));
            CheckTest.checkOk(n, min(Double.NEGATIVE_INFINITY));
            CheckTest.checkOk(n, min(Double.NaN));
            CheckTest.checkOk(Float.POSITIVE_INFINITY, min(n));
            CheckTest.checkOk(Double.POSITIVE_INFINITY, min(n));
            CheckTest.checkOk(Float.NaN, min(n));
            CheckTest.checkOk(Double.NaN, min(n));

            CheckTest.checkFail(n, min(Float.POSITIVE_INFINITY));
            CheckTest.checkFail(n, min(Double.POSITIVE_INFINITY));
            CheckTest.checkFail(Float.NEGATIVE_INFINITY, min(n));
            CheckTest.checkFail(Double.NEGATIVE_INFINITY, min(n));
        });
    }

    @Test
    public void checkMax() {
        RandomUtil.randomNumberSuppliers().forEach(s -> {

            Number a = s.get();
            Number b = s.get();
            if (new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString())) > 0) {
                Number temp = a;
                a = b;
                b = temp;
            }
            CheckTest.checkOk(a, max(a));
            CheckTest.checkOk(a, max(b));

            Number n = s.get();
            CheckTest.checkOk(n, max(Float.POSITIVE_INFINITY));
            CheckTest.checkOk(n, max(Double.POSITIVE_INFINITY));
            CheckTest.checkOk(Float.NEGATIVE_INFINITY, max(n));
            CheckTest.checkOk(Float.NaN, max(n));
            CheckTest.checkOk(Double.NEGATIVE_INFINITY, max(n));
            CheckTest.checkOk(Double.NaN, max(n));
            CheckTest.checkOk(Float.NaN, max(n));
            CheckTest.checkOk(Double.NaN, max(n));

            CheckTest.checkFail(n, max(Float.NEGATIVE_INFINITY));
            CheckTest.checkFail(n, max(Double.NEGATIVE_INFINITY));
            CheckTest.checkFail(Float.POSITIVE_INFINITY, max(n));
            CheckTest.checkFail(Double.POSITIVE_INFINITY, max(n));
        });
    }

    @Test
    public void checkLessThan() {
        CheckTest.checkFail(5d, lessThan(5));

        CheckTest.checkOk(5.32, lessThan(5));
        CheckTest.checkFail(-20, lessThan(-15));
    }

    @Test
    public void checkGreaterThan() {
        CheckTest.checkFail(5d, greaterThan(5));

        CheckTest.checkOk(199, greaterThan(10));
        CheckTest.checkFail(-99, greaterThan(-5));
    }

    private final static BigDecimal minOf(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    private final static BigDecimal maxOf(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
}
