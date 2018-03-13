package ch.frostnova.keygen.model;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.keygen.alphabet.impl.*;
import ch.frostnova.util.check.Check;
import ch.frostnova.util.check.CheckString;

import java.util.NoSuchElementException;

/**
 * Created by pwalser on 20.02.2017.
 */
public enum KeyType {

    /**
     * Key type: numeric, from '0'-'9'
     */
    Numeric(new NumericAlphabet()),

    /**
     * Key type: alpha-numeric, from '0'-'9', 'A'-'Z', 'a'-'z'
     */
    AlphaNumeric(new AlphanumericAlphabet()),

    /**
     * Key type: hexadecimal, from '0'-'9', 'A'-'F'
     */
    Hexadecimal(new HexadecimalAlphabet()),

    /**
     * Key type: alpha-numeric, without similar characters (l,I,1, o,O,0 and B,8)
     */
    SaveAlphaNumeric(new SaveAlphanumericAlphabet()),

    /**
     * Key type: alpha-numeric, without any remotely similar characters (l,I,1, o,O,0 and B,8, s,S, z,Z,2, u,U, w,W,
     * x,X)
     */
    VerySaveAlphaNumeric(new VerySaveAlphanumericAlphabet());

    private Alphabet alphabet;

    KeyType(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public static KeyType parse(String input) {
        input = Check.required(input, "input", CheckString.notBlank()).trim();
        for (KeyType value : values()) {
            if (value.name().equalsIgnoreCase(input)) {
                return value;
            }
        }
        throw new NoSuchElementException("No such " + KeyType.class.getSimpleName() + ": " + input);
    }
}
