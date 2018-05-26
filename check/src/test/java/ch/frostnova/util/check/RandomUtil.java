package ch.frostnova.util.check;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Random value generators for monte carlo testing.
 *
 * @author pwalser
 * @since 26.05.2018.
 */
public final class RandomUtil {

    private RandomUtil() {

    }

    private static double randomPlusMinus() {
        return ThreadLocalRandom.current().nextDouble() * 2 - 1;
    }

    private static BigInteger random(BigInteger min, BigInteger max) {
        return random(new BigDecimal(min), new BigDecimal(max)).toBigInteger();
    }

    private static BigDecimal random(BigDecimal min, BigDecimal max) {
        return min.add(max.subtract(min).multiply(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble())));
    }

    public static Stream<Supplier<Number>> randomNumberSuppliers() {
        return Stream.of(RandomUtil::randomByte, RandomUtil::randomShort, RandomUtil::randomInt, RandomUtil::randomLong,
                RandomUtil::randomFloat, RandomUtil::randomDouble, RandomUtil::randomBigInteger, RandomUtil::randomBigDecimal);
    }

    public static byte randomByte() {
        final BigInteger min = BigInteger.valueOf(Byte.MIN_VALUE);
        final BigInteger max = BigInteger.valueOf(Byte.MAX_VALUE);
        return random(min, max).byteValue();
    }

    public static short randomShort() {
        final BigInteger min = BigInteger.valueOf(Short.MIN_VALUE);
        final BigInteger max = BigInteger.valueOf(Short.MAX_VALUE);
        return random(min, max).shortValue();
    }

    public static int randomInt() {
        final BigInteger min = BigInteger.valueOf(Integer.MIN_VALUE);
        final BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
        return random(min, max).intValue();
    }

    public static long randomLong() {
        final BigInteger min = BigInteger.valueOf(Long.MIN_VALUE);
        final BigInteger max = BigInteger.valueOf(Long.MAX_VALUE);
        return random(min, max).longValue();
    }

    public static float randomFloat() {
        final BigDecimal min = BigDecimal.valueOf(-Float.MIN_VALUE);
        final BigDecimal max = BigDecimal.valueOf(Float.MAX_VALUE);
        return random(min, max).floatValue();
    }

    public static double randomDouble() {
        final BigDecimal min = BigDecimal.valueOf(-Double.MIN_VALUE);
        final BigDecimal max = BigDecimal.valueOf(Double.MAX_VALUE);
        return random(min, max).doubleValue();
    }

    public static BigInteger randomBigInteger() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int bits = random.nextInt(1, 1000);
        return new BigInteger(bits, random);
    }

    public static BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf((long) (Long.MAX_VALUE * randomPlusMinus()), -100000);
    }

}
