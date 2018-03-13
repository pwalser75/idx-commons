package ch.frostnova.keygen;

import ch.frostnova.keygen.model.KeyLengthUnit;
import ch.frostnova.keygen.model.KeySpec;
import ch.frostnova.keygen.model.KeyType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for {@link KeySpec} Created by pwalser on 20.02.2017.
 */
public class KeySpecTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMissingType() {
        new KeySpec(null, 123, KeyLengthUnit.Bits);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingUnit() {
        new KeySpec(KeyType.Numeric, 123, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeLength() {
        new KeySpec(KeyType.Numeric, -5, KeyLengthUnit.Bits);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroLength() {
        new KeySpec(KeyType.Numeric, 0, KeyLengthUnit.Bits);
    }

    @Test
    public void testValid() {
        KeySpec KeySpec = new KeySpec(KeyType.Numeric, 123, KeyLengthUnit.Bits);
        Assert.assertEquals(KeyType.Numeric, KeySpec.getType());
        Assert.assertEquals(123, KeySpec.getLength());
        Assert.assertEquals(KeyLengthUnit.Bits, KeySpec.getUnit());
    }

    @Test
    public void testParseValid() {
        for (String s : Arrays.asList("Numeric123Bits", "NUMERIC123bits", "NuMerIC 123   BITs")) {
            KeySpec KeySpec = new KeySpec(s);
            Assert.assertEquals(KeyType.Numeric, KeySpec.getType());
            Assert.assertEquals(123, KeySpec.getLength());
            Assert.assertEquals(KeyLengthUnit.Bits, KeySpec.getUnit());
        }
        for (String s : Arrays.asList("AlphaNumeric543Bytes", "ALPHANUMERIC543bytes", "  alphaNuMerIC 543  BYTEs")) {
            KeySpec KeySpec = new KeySpec(s);
            Assert.assertEquals(KeyType.AlphaNumeric, KeySpec.getType());
            Assert.assertEquals(543, KeySpec.getLength());
            Assert.assertEquals(KeyLengthUnit.Bytes, KeySpec.getUnit());
        }
        for (String s : Arrays.asList("Hexadecimal9Chars", "HEXaDECimal9CHaracters", " hexAdECIMal 9   CharS", "HExadecimal 9 Digits")) {
            KeySpec KeySpec = new KeySpec(s);
            Assert.assertEquals(KeyType.Hexadecimal, KeySpec.getType());
            Assert.assertEquals(9, KeySpec.getLength());
            Assert.assertEquals(KeyLengthUnit.Chars, KeySpec.getUnit());
        }
    }

    @Test
    public void testParseInvalidValid() {

        try {
            new KeySpec("");
            Assert.fail("Expected: " + IllegalArgumentException.class.getName());
        } catch (IllegalArgumentException expected) {
        }
        for (String s : Arrays.asList(
                "ABC", "Cryptic123BITS", "Numeric6Potatoes", "AlphanumericChars", "5 charz", "12 monkeys")) {
            try {
                new KeySpec(s);
                Assert.fail("Expected: " + IllegalArgumentException.class.getName());
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    @Test
    public void testKeySpecStrenght() {

        for (int i = 0; i < 10; i++) {

            int rnd = 1 + (int) (Math.random() * 100);

            for (KeyType keyType : KeyType.values()) {
                Assert.assertEquals(rnd, new KeySpec(keyType, rnd, KeyLengthUnit.Bits).getStrengthInBits());
                Assert.assertEquals(rnd * 8, new KeySpec(keyType, rnd, KeyLengthUnit.Bytes).getStrengthInBits());
            }
        }

        Assert.assertEquals(4, new KeySpec("Hexadecimal1Chars").getStrengthInBits());
        Assert.assertEquals(428, new KeySpec("Alphanumeric72Chars").getStrengthInBits());
        Assert.assertEquals(100, new KeySpec("Hexadecimal25Chars").getStrengthInBits());
        Assert.assertEquals(19, new KeySpec("Numeric6Chars").getStrengthInBits());
        Assert.assertEquals(569, new KeySpec("SaveAlphaNumeric99Chars").getStrengthInBits());
    }

}
