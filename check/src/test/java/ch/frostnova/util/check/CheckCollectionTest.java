package ch.frostnova.util.check;

import org.junit.Test;

import java.util.*;

/**
 * Test for {@link CheckCollection}
 *
 * @author pwalser
 * @since 03.09.2017.
 */
public class CheckCollectionTest {

    @Test
    public void checkNotEmpty() {
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.notEmpty());
        CheckTest.checkFail(new LinkedList(), CheckCollection.notEmpty());
        CheckTest.checkFail(Collections.emptyList(), CheckCollection.notEmpty());

        CheckTest.checkOk(new HashSet(Arrays.asList(1, 2, 3)), CheckCollection.notEmpty());
        CheckTest.checkFail(new HashSet(), CheckCollection.notEmpty());
        CheckTest.checkFail(Collections.emptySet(), CheckCollection.notEmpty());
    }

    @Test
    public void checkNoNullElements() {
        CheckTest.checkOk(new ArrayList(), CheckCollection.noNullElements());
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.noNullElements());
        CheckTest.checkFail(Arrays.asList("Aloha", null), CheckCollection.noNullElements());

        CheckTest.checkOk(new HashSet<>(), CheckCollection.noNullElements());
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.noNullElements());
        CheckTest.checkFail(new HashSet<>(Arrays.asList("Aloha", null)), CheckCollection.noNullElements());
    }

    @Test
    public void checkMin() {
        CheckTest.checkFail(Collections.emptyList(), CheckCollection.min(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.min(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.min(3));
        CheckTest.checkFail(Arrays.asList(1, 2, 3), CheckCollection.min(4));

        CheckTest.checkFail(Collections.emptyList(), CheckCollection.min(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.min(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.min(3));
        CheckTest.checkFail(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.min(4));
    }

    @Test
    public void checkMax() {
        CheckTest.checkOk(Collections.emptyList(), CheckCollection.max(2));
        CheckTest.checkFail(Arrays.asList(1, 2, 3), CheckCollection.max(2));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.max(3));
        CheckTest.checkOk(Arrays.asList(1, 2, 3), CheckCollection.max(4));

        CheckTest.checkOk(Collections.emptyList(), CheckCollection.max(2));
        CheckTest.checkFail(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.max(2));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.max(3));
        CheckTest.checkOk(new HashSet<>(Arrays.asList(1, 2, 3)), CheckCollection.max(4));
    }
}
