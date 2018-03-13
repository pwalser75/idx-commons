package ch.frostnova.keygen.model;

import ch.frostnova.util.check.Check;
import ch.frostnova.util.check.CheckNumber;
import ch.frostnova.util.check.CheckString;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Key specification Created by pwalser on 20.02.2017.
 */
public final class KeySpec {

    private final static Pattern PATTERN = Pattern.compile("\\s*([A-Za-z]+)\\s*([0-9]+)\\s*([A-Za-z]+)\\s*");

    private final KeyType type;
    private final int length;
    private final KeyLengthUnit unit;

    /**
     * Creates a new OPT specification
     *
     * @param type    type, required
     * @param length, required, must be &gt; 0
     * @param unit    unit, required
     */
    public KeySpec(KeyType type, int length, KeyLengthUnit unit) {

        this.type = Check.required(type, "type");
        this.length = Check.required(length, "length", CheckNumber.min(1));
        this.unit = Check.required(unit, "unit");
    }

    public KeySpec(String specification) {
        Check.required(specification, "specification", CheckString.notBlank());

        Matcher matcher = PATTERN.matcher(specification);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unsupported specification: " + specification);
        }
        try {
            type = KeyType.parse(matcher.group(1));
            length = Integer.parseInt(matcher.group(2));
            unit = KeyLengthUnit.parse(matcher.group(3));
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Unsupported specification: " + specification + ": " + ex.getMessage());
        }
    }

    public KeyType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public int getStrengthInBits() {

        if (unit == KeyLengthUnit.Bits) {
            return length;
        }
        if (unit == KeyLengthUnit.Bytes) {
            return length * 8;
        }
        if (unit == KeyLengthUnit.Chars) {
            return (int) (Math.log(Math.pow(type.getAlphabet().length(), length)) / Math.log(2));
        }
        throw new IllegalStateException("Unsuported key length unit");
    }

    public KeyLengthUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return type.name() + length + unit.name();
    }
}
