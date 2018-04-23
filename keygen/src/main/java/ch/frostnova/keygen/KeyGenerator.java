package ch.frostnova.keygen;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.keygen.alphabet.AlphabetCodec;
import ch.frostnova.keygen.model.KeyLengthUnit;
import ch.frostnova.keygen.model.KeySpec;
import ch.frostnova.keygen.model.KeyType;
import ch.frostnova.util.check.Check;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Key generator, generates keys according to provided key specs.
 *
 * @author pwalser
 * @since 21.02.2017
 */
public final class KeyGenerator {

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            System.exit(1);
        }
        try {
            KeySpec keySpec = new KeySpec(args[0]);
            System.out.println("Key Spec: " + keySpec);
            System.out.println("Key strength [bits]: " + keySpec.getStrengthInBits());
            System.out.println(generate(keySpec));
        } catch (Exception ex) {
            System.err.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            printUsage();
            System.exit(1);
        }
    }

    private KeyGenerator() {

    }

    private static void printUsage() {
        System.out.println("Usage: java -jar secure-keygen.jar {keyspec}");
        System.out.println("   {keyspec}: Key specification in the format {keytypes}{lenght}{lengthunit}");
        System.out.println("example keyspecs: Numeric6Chars, AlphaNumeric256bit, Hexadecimal8bytes");
        System.out.println("supported keytypes: " + Stream.of(KeyType.values()).map(Enum::name).collect(Collectors.joining(", ")));
        System.out.println("supported lengthunits: " + Stream.of(KeyLengthUnit.values()).map(Enum::name).collect(Collectors.joining(", ")));
    }

    public static String generate(KeySpec spec) {
        return generate(spec, new SecureRandom());
    }

    public static String generate(KeySpec spec, Random random) {
        Check.required(spec, "OTP specification");
        Check.required(random, "random");

        Alphabet alphabet = spec.getAlphabet();
        AlphabetCodec codec = new AlphabetCodec(alphabet);

        if (spec.getUnit() == KeyLengthUnit.Bits) {
            BigInteger value = new BigInteger(spec.getLength(), random);
            return codec.encode(value);
        } else if (spec.getUnit() == KeyLengthUnit.Bytes) {
            BigInteger value = new BigInteger(spec.getLength() * 8, random);
            return codec.encode(value);
        } else if (spec.getUnit() == KeyLengthUnit.Chars) {
            BigInteger max = BigInteger.valueOf(alphabet.length()).pow(spec.getLength());
            BigInteger value;
            do {
                value = new BigInteger(max.bitLength(), random);
            } while (value.compareTo(max) >= 0);
            StringBuilder encoded = new StringBuilder(codec.encode(value));
            // padding in front to guarantee [length] characters
            while (encoded.length() < spec.getLength()) {
                encoded.insert(0, alphabet.charAt(0));
            }
            return encoded.toString();
        }
        throw new IllegalStateException("Unknown unit: " + spec.getUnit());
    }
}
