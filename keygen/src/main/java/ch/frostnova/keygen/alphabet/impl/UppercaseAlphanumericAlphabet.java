package ch.frostnova.keygen.alphabet.impl;

import ch.frostnova.keygen.alphabet.Alphabet;

import java.util.NoSuchElementException;

/**
 * Alphabet consisting of characters '0'-'9', 'A'-'Z'
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class UppercaseAlphanumericAlphabet implements Alphabet {

    @Override
    public int length() {
        return 36;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= 36) {
            throw new IndexOutOfBoundsException();
        }
        if (index < 10) {
            return (char) ('0' + index);
        }
        return (char) ('A' + index - 10);
    }

    @Override
    public int indexOf(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('A' <= c && c <= 'Z') {
            return c - 'A' + 10;
        }
        throw new NoSuchElementException();
    }
}
