package ch.frostnova.keygen.alphabet.impl;

import ch.frostnova.keygen.alphabet.Alphabet;

import java.util.NoSuchElementException;

/**
 * Alphabet consisting of characters '0'-'9', 'A'-'F'
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class HexadecimalAlphabet implements Alphabet {

    @Override
    public int length() {
        return 16;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= 16) {
            throw new IndexOutOfBoundsException();
        }
        return (index < 10) ? (char) ('0' + index) : (char) ('A' + index - 10);
    }

    @Override
    public int indexOf(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('A' <= c && c <= 'F') {
            return c - 'A' + 10;
        }
        throw new NoSuchElementException();
    }
}
