package ch.frostnova.keygen;

import ch.frostnova.keygen.model.KeyPattern;
import ch.frostnova.keygen.model.KeySpec;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Test for {@link KeyPattern}
 *
 * @author pwalser
 * @since 22.02.2017
 */
public class KeyPatternTest {

    @Test
    public void testValidPatterns() {

        String[] patterns = new String[]{"#", "##", "#*", "##-#", "##-#*"};
        for (String pattern : patterns) {
            new KeyPattern(pattern);
        }
    }

    @Test
    public void testInvalidPatterns() {

        String[] patterns = new String[]{null, "", "*", "abc", "#*#", "abc*"};
        for (String pattern : patterns) {
            try {
                new KeyPattern(pattern);
                Assert.fail("Expected: " + IllegalArgumentException.class.getName());
            } catch (IllegalArgumentException expected) {

            }
        }
    }

    @Test
    public void testTrivialFormat() {
        KeyPattern pattern = new KeyPattern("#");
        Assert.assertEquals("12345", pattern.format("12345"));
    }

    @Test
    public void testDashedFormat() {
        KeyPattern pattern = new KeyPattern("#-*");
        Assert.assertEquals("1-2-3-4-5", pattern.format("12345"));
    }

    @Test
    public void testUUIDFormat() {
        Assert.assertEquals("7dc74450-69c5-4b7e-aa6e-3994f23c1668", KeyPattern.UUID.format("7dc7445069c54b7eaa6e3994f23c1668"));
    }

    @Test
    public void testLicenseKeyFormat() {
        System.out.println(KeyGenerator.generate(new KeySpec("alphanumeric16digits")));
        Assert.assertEquals("x4ZY-AOV1-lylq-TtoO", KeyPattern.LICENSE_KEY.format("x4ZYAOV1lylqTtoO"));
    }

    @Test
    public void testVariousFormats() {

        System.out.println(UUID.randomUUID());
        Assert.assertEquals("ABCD-EFGHIJKLMNOP", new KeyPattern("####-").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("ABCD-EFGH-IJKL-MNOP", new KeyPattern("####-*").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("AB_CDE_FGH_IJK_LMN_OP", new KeyPattern("##_#*").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("ABCD EFGH IJKL MNOP", new KeyPattern("#### *").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("ABCD | EFGH | IJKL | MNOP", new KeyPattern("#### | *").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("/ABCD/EFGH/IJKL/MNOP", new KeyPattern("/####*").format("ABCDEFGHIJKLMNOP"));
        Assert.assertEquals("~ABCD.EF.GHIJKLMNOP", new KeyPattern("~####.##.").format("ABCDEFGHIJKLMNOP"));
    }
}
