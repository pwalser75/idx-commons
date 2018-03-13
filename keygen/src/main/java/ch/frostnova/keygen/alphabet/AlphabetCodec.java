package ch.frostnova.keygen.alphabet;

import ch.frostnova.util.check.Check;

import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * Alphabet based codec, converts BigIntegers into a number system represented by a given alphabet (characters being the
 * digits, base the length of the alphabet).
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class AlphabetCodec {

    private final Alphabet alphabet;

    public AlphabetCodec(Alphabet alphabet) {
        this.alphabet = Check.required(alphabet, "alphabet");
        if (alphabet.length() < 2) {
            throw new IllegalStateException("Alphabet is too short to be used for encoding/decoding");
        }
    }

    public String encode(BigInteger value) {
        if (value == null) {
            return null;
        }
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Only unsigned/positive values can be encoded");
        }
        if (value.equals(BigInteger.ZERO)) {
            return String.valueOf(alphabet.charAt(0));
        }
        BigInteger len = BigInteger.valueOf(alphabet.length());

        StringBuilder builder = new StringBuilder();
        while (!value.equals(BigInteger.ZERO)) {
            int index = value.mod(len).intValue();
            builder.insert(0, alphabet.charAt(index));
            value = value.divide(len);
        }

        return builder.toString();
    }

    public BigInteger decode(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        BigInteger len = BigInteger.valueOf(alphabet.length());
        BigInteger result = BigInteger.ZERO;
        for (char c : value.toCharArray()) {
            try {
                int index = alphabet.indexOf(c);
                result = result.multiply(len).add(BigInteger.valueOf(index));
            } catch (NoSuchElementException ex) {
                throw new IllegalArgumentException("unrecognized character: " + c);
            }
        }
        return result;

    }
}
