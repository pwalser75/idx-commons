package ch.frostnova.util.check;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

        Consumer<ZonedDateTime> futureDate = d -> {
            if (!d.isAfter(ZonedDateTime.now())) {
                throw new IllegalArgumentException("must be in the future");
            }
        };

        Check.required(date, "execution date", futureDate);
    }

    @Test
    public void checkWithPredicate1() {

        Predicate<String> hasUppercaseLetters = s -> s.chars().anyMatch(Character::isUpperCase);
        Predicate<String> hasLowercaseLetters = s -> s.chars().anyMatch(Character::isLowerCase);
        Predicate<String> hasDigits = s -> s.chars().anyMatch(Character::isDigit);
        Predicate<String> passwordRule = hasUppercaseLetters.and(hasLowercaseLetters.and(hasDigits));
        String message = "must contain lower/uppercase characters and digits";

        Check.required("ThePassword123", "password", Check.is(passwordRule, message), CheckString.min(6));

        try {
            Check.required("foo", "password", Check.is(passwordRule, message), CheckString.min(6));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains(message));
        }
    }

    @Test
    public void checkWithPredicate2() {

        Check.required(1234, "number",
                Check.is(i -> (i.intValue() & 1) == 0, "must be even"));

        try {
            Check.required(5555, "number",
                    Check.is(i -> (i.intValue() & 1) == 0, "must be even"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("must be even"));
        }
    }

    @Test
    public void checkWithPredicateAndDynamicFunction1() {

        Check.required(LocalDate.of(1975, 12, 20), "date",
                Check.is(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY, v -> v + " is not a saturday"));

        try {
            Check.required(LocalDate.of(2020, 2, 20), "date",
                    Check.is(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY, v -> v + " is not a saturday"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("2020-02-20 is not a saturday"));
        }
    }

    @Test
    public void checkWithPredicateAndDynamicFunction2() {

        Check.required(1234, "number",
                Check.is(i -> (i.intValue() & 1) == 0, v -> v + " must be even"));

        try {
            Check.required(5555, "number",
                    Check.is(i -> (i.intValue() & 1) == 0, v -> v + " must be even"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("5555 must be even"));
        }
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
        String parameterName = "Test value: " + value;
        T result = Check.required(value, parameterName, validators);
        Assert.assertEquals(value, result);

    }

    public static <T> void checkFail(T value, Consumer<? super T>... validators) {
        String parameterName = "Test value: " + value;
        try {
            Check.required(value, parameterName, validators);
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("'" + parameterName + "'"));
        }
    }
}
