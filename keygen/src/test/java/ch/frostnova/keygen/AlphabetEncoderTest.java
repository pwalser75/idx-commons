package ch.frostnova.keygen;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.keygen.alphabet.AlphabetCodec;
import ch.frostnova.keygen.alphabet.impl.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Alphabet tests
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class AlphabetEncoderTest {

    @Test
    public void testNumericAlphabet() {
        testEncodingDecoding(new NumericAlphabet());
    }

    @Test
    public void testAlphaNumericAlphabet() {
        testEncodingDecoding(new AlphanumericAlphabet());
    }

    @Test
    public void testSaveAlphaNumericAlphabet() {
        testEncodingDecoding(new SaveAlphanumericAlphabet());
    }

    @Test
    public void testDashAlphabet() {
        testEncodingDecoding(new PredefinedAlphabet("–-—"));
    }

    @Test
    public void testHexAlphabet() {
        testEncodingDecoding(new HexadecimalAlphabet());
    }

    @Test
    public void testBinaryPredefinedAlphabet() {
        testEncodingDecoding(new PredefinedAlphabet("01"));
    }

    @Test
    public void testXYZPredefinedAlphabet() {
        testEncodingDecoding(new PredefinedAlphabet("XYZxyz"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodingInvalidAlphabet() {

        String encoded = "12345";
        AlphabetCodec codec = new AlphabetCodec(new PredefinedAlphabet("123"));
        codec.decode(encoded);
    }

    private void testEncodingDecoding(Alphabet alphabet) {

        AlphabetCodec codec = new AlphabetCodec(alphabet);

        // trivial
        String encoded = codec.encode(BigInteger.ZERO);
        Assert.assertNotNull(encoded);
        Assert.assertTrue(encoded.length() == 1);
        Assert.assertEquals(alphabet.charAt(0), encoded.charAt(0));

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 100; i++) {

            int bits = ThreadLocalRandom.current().nextInt(1, 500);
            // encoding
            BigInteger value = new BigInteger(bits, secureRandom);
            encoded = codec.encode(value);

            // decoding
            BigInteger decoded = codec.decode(encoded);
            Assert.assertEquals(value, decoded);
        }
    }
}
