package ch.frostnova.util.check;

import org.junit.Test;

import java.util.*;

import static ch.frostnova.util.check.CheckCollection.*;

/**
 * Test for {@link CheckCollection}
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckCollectionTest {

    @Test
    public void checkNotEmpty() {
        CheckTest.checkOk(Arrays.asList(1, 2, 3), notEmpty());
        CheckTest.checkFail(new LinkedList(), notEmpty());
        CheckTest.checkFail(Collections.emptyList(), notEmpty());

        CheckTest.checkOk(new HashSet(Arrays.asList(1, 2, 3)), notEmpty());
        CheckTest.checkFail(new HashSet(), notEmpty());
        CheckTest.checkFail(Collections.emptySet(), notEmpty());

        Verify<List> c = Verify.that(l -> l.isEmpty(), "foo");
        CheckTest.checkFail(Collections.emptyList(), notEmpty(), c);
    }

    @Test
    public void checkNoNullElements() {
        CheckTest.checkOk(new ArrayList(), noNullElements());
        CheckTest.checkOk(Arrays.asList(1, 2, 3), noNullElements());
        CheckTest.checkFail(Arrays.asList("Aloha", null), noNullElements());

        CheckTest.checkOk(new HashSet<>(), noNullElements());
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), noNullElements());
        CheckTest.checkFail(new HashSet<>(Arrays.asList("Aloha", null)), noNullElements());
    }

    @Test
    public void checkMin() {
        CheckTest.checkFail(Collections.emptyList(), min(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), min(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), min(3));
        CheckTest.checkFail(Arrays.asList(1, 2, 3), min(4));

        CheckTest.checkFail(Collections.emptyList(), min(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), min(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), min(3));
        CheckTest.checkFail(new HashSet<>(Arrays.asList(1, 2, 3)), min(4));
    }

    @Test
    public void checkMax() {
        CheckTest.checkOk(Collections.emptyList(), max(2));
        CheckTest.checkFail(Arrays.asList(1, 2, 3), max(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), max(3));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), max(4));

        CheckTest.checkOk(Collections.emptyList(), max(2));
        CheckTest.checkFail(new HashSet<>(Arrays.asList(1, 2, 3)), max(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), max(3));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), max(4));
    }
}
