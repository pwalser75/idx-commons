package ch.frostnova.keygen;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.keygen.alphabet.impl.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Alphabet tests
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class AlphabetTest {

    @Test
    public void testDigitsAlphabet() {
        Alphabet alphabet = new NumericAlphabet();
        Assert.assertEquals(10, alphabet.length());
        Assert.assertEquals("0123456789", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testAlphaNumericAlphabet() {
        Alphabet alphabet = new AlphanumericAlphabet();
        Assert.assertEquals(62, alphabet.length());
        Assert.assertEquals("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testSaveAlphaNumericAlphabet() {
        Alphabet alphabet = new SaveAlphanumericAlphabet();
        Assert.assertEquals(54, alphabet.length());
        Assert.assertEquals("2345679ACDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testPredefinedAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz+-*/+%&()=??????!£$äöüàéèÄÖÜ@#");
        Assert.assertEquals(86, alphabet.length());
        Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz+-*/%&()=?!£$äöüàéèÄÖÜ@#", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testCrypticPredefinedAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet("&%#@+!?$£");
        Assert.assertEquals(9, alphabet.length());
        Assert.assertEquals("&%#@+!?$£", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testAnotherCrypticPredefinedAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet(".,;:´`");
        Assert.assertEquals(6, alphabet.length());
        Assert.assertEquals(".,;:´`", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testDashAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet("–-—");
        Assert.assertEquals(3, alphabet.length());
        Assert.assertEquals("–-—", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testHexAlphabet() {
        Alphabet alphabet = new HexadecimalAlphabet();
        Assert.assertEquals(16, alphabet.length());
        Assert.assertEquals("0123456789ABCDEF", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test
    public void testBinaryPredefinedAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet("01");
        Assert.assertEquals(2, alphabet.length());
        Assert.assertEquals("01", alphabet.allChars());
        testAlphabet(alphabet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooShortAlphabet() {
        Alphabet alphabet = new PredefinedAlphabet("x");
        Assert.assertEquals(1, alphabet.length());
        Assert.assertEquals("x", alphabet.allChars());
        testAlphabet(alphabet);
    }

    private void testAlphabet(Alphabet alphabet) {
        testCharacterMapping(alphabet);
        testBounds(alphabet);
    }

    private void testCharacterMapping(Alphabet alphabet) {
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            Assert.assertEquals(i, alphabet.indexOf(c));
        }
    }

    private void testBounds(Alphabet alphabet) {
        try {
            alphabet.charAt(-1);
            Assert.fail("Expected: " + IndexOutOfBoundsException.class);
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            alphabet.charAt(alphabet.length());
            Assert.fail("Expected: " + IndexOutOfBoundsException.class);
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            alphabet.indexOf('\0');
            Assert.fail("Expected: " + NoSuchElementException.class);
        } catch (NoSuchElementException expected) {
        }
    }
}
