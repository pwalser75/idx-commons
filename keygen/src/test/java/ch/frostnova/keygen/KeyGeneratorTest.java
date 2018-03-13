package ch.frostnova.keygen;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.keygen.alphabet.AlphabetCodec;
import ch.frostnova.keygen.model.KeyLengthUnit;
import ch.frostnova.keygen.model.KeySpec;
import ch.frostnova.keygen.model.KeyType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by pwalser on 20.02.2017.
 */
public class KeyGeneratorTest {

    @Test
    public void testOutput() {
        for (String s : Arrays.asList("Numeric6Chars", "alphanumeric 32 chars", "AlphaNumeric 256 bits",
                "Hexadecimal 5BYTES", "SaveAlphaNumeric20byte", "verySaveAlphaNumeric60bit")) {
            KeySpec keySpec = new KeySpec(s);
            System.out.println(keySpec + ": " + KeyGenerator.generate(keySpec));
        }
    }

    @Test
    public void testGenerateNumericKey() {
        testKey(KeyType.Numeric);
    }

    @Test
    public void testGenerateAlphaNumericKey() {
        testKey(KeyType.AlphaNumeric);
    }

    @Test
    public void testGenerateSaveAlphaNumericKey() {
        testKey(KeyType.SaveAlphaNumeric);
    }

    @Test
    public void testGenerateHexKey() {
        testKey(KeyType.Hexadecimal);
    }

    private void testKey(KeyType type) {
        for (int l = 1; l < 100; l++) {
            String key = KeyGenerator.generate(new KeySpec(type, l, KeyLengthUnit.Bits));
            testKey(key, type.getAlphabet(), BigInteger.valueOf(2).pow(l));
        }
        for (int l = 1; l < 100; l++) {
            String key = KeyGenerator.generate(new KeySpec(type, l, KeyLengthUnit.Bits));
            testKey(key, type.getAlphabet(), BigInteger.valueOf(2).pow(l + 3));
        }
        for (int l = 1; l < 100; l++) {
            String key = KeyGenerator.generate(new KeySpec(type, l, KeyLengthUnit.Chars));
            Assert.assertEquals(l, key.length());
            testKey(key, type.getAlphabet(), BigInteger.valueOf(type.getAlphabet().length()).pow(l));
        }
    }

    private void testKey(String key, Alphabet alphabet, BigInteger max) {
        Assert.assertNotNull(key);
        BigInteger decoded = new AlphabetCodec(alphabet).decode(key);
        Assert.assertTrue(decoded.compareTo(BigInteger.ZERO) >= 0);
        Assert.assertTrue(decoded.compareTo(max) < 0);
    }

}
