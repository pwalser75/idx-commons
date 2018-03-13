package ch.frostnova.keygen.model;

import ch.frostnova.util.check.Check;
import ch.frostnova.util.check.CheckString;

import java.util.NoSuchElementException;

/**
 * Created by pwalser on 20.02.2017.
 */
public enum KeyLengthUnit {

    /**
     * bytes
     */
    Bits("Bit"),

    /**
     * bytes
     */
    Bytes("Byte"),

    /**
     * characters
     */
    Chars("Char", "Characters", "Digit", "Digits");

    private String[] aliases;

    KeyLengthUnit(String... aliases) {
        this.aliases = aliases;
    }

    public static KeyLengthUnit parse(String input) {
        input = Check.required(input, "input", CheckString.notBlank()).trim();

        for (KeyLengthUnit value : values()) {
            if (value.name().equalsIgnoreCase(input)) {
                return value;
            }
            for (String alias : value.aliases) {
                if (alias.equalsIgnoreCase(input)) {
                    return value;
                }
            }
        }
        throw new NoSuchElementException("No such " + KeyLengthUnit.class.getSimpleName() + ": " + input);
    }
}
