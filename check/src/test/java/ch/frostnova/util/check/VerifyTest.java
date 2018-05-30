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
    public void checkChainVerify() {

        Verify<String> hasUppercaseLetters = Verify.that(s -> s.chars().anyMatch(Character::isUpperCase), "must contain uppercase letter(s)");
        Verify<String> hasLowercaseLetters = Verify.that(s -> s.chars().anyMatch(Character::isLowerCase), "must contain lowercase letter(s)");
        Verify<String> hasDigits = Verify.that(s -> s.chars().anyMatch(Character::isDigit), "must contain digit(s)");
        Verify<String> atLeast6Characters = CheckString.min(6);
        Verify<String> passwordRule = hasUppercaseLetters.and(hasLowercaseLetters).and(hasDigits).and(atLeast6Characters);

        Check.required("ThePassword123", "password", passwordRule);

        try {
            Check.required("Foo", "password", passwordRule, CheckString.min(6));
            Assert.fail("Expected: " + IllegalArgumentException.class.getSimpleName());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
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
