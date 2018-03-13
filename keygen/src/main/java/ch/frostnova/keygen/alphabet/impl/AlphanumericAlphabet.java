package ch.frostnova.keygen.alphabet.impl;

import ch.frostnova.keygen.alphabet.Alphabet;

import java.util.NoSuchElementException;

/**
 * Alphabet consisting of characters '0'-'9', 'A'-'Z', 'a'-'z'
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class AlphanumericAlphabet implements Alphabet {

    @Override
    public int length() {
        return 62;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= 62) {
            throw new IndexOutOfBoundsException();
        }
        if (index < 10) {
            return (char) ('0' + index);
        }
        if (index < 36) {
            return (char) ('A' + index - 10);
        }
        return (char) ('a' + index - 36);
    }

    @Override
    public int indexOf(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('A' <= c && c <= 'Z') {
            return c - 'A' + 10;
        }
        if ('a' <= c && c <= 'z') {
            return c - 'a' + 36;
        }
        throw new NoSuchElementException();
    }
}
