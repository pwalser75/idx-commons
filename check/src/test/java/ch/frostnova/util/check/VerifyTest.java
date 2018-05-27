package ch.frostnova.util.check;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Test for {@link Verify} functional interface
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class VerifyTest {

    @Test
    public void checkWithPredicate1() {

        Predicate<String> hasUppercaseLetters = s -> s.chars().anyMatch(Character::isUpperCase);
        Predicate<String> hasLowercaseLetters = s -> s.chars().anyMatch(Character::isLowerCase);
        Predicate<String> hasDigits = s -> s.chars().anyMatch(Character::isDigit);
        Predicate<String> passwordRule = hasUppercaseLetters.and(hasLowercaseLetters.and(hasDigits));
        String message = "must contain lower/uppercase characters and digits";

        Check.required("ThePassword123", "password", Verify.that(passwordRule, message), CheckString.min(6));

        try {
            Check.required("foo", "password", Verify.that(passwordRule, message), CheckString.min(6));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains(message));
        }
    }

    @Test
    public void checkWithPredicate2() {

        Check.required(1234, "number",
                Verify.that(i -> (i & 1) == 0, "must be even"));

        try {
            Check.required(5555, "number",
                    Verify.that(i -> (i & 1) == 0, "must be even"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("must be even"));
        }
    }

    @Test
    public void checkWithPredicateAndDynamicFunction1() {

        Check.required(LocalDate.of(1975, 12, 20), "date",
                Verify.that(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY, v -> v + " that not a saturday"));

        try {
            Check.required(LocalDate.of(2020, 2, 20), "date",
                    Verify.that(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY, v -> v + " is not a saturday"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("2020-02-20 is not a saturday"));
        }
    }

    @Test
    public void checkWithPredicateAndDynamicFunction2() {

        Check.required(1234, "number",
                Verify.that(i -> (i & 1) == 0, v -> v + " must be even"));

        try {
            Check.required(5555, "number",
                    Verify.that(i -> (i & 1) == 0, v -> v + " must be even"));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("5555 must be even"));
        }
    }
}
