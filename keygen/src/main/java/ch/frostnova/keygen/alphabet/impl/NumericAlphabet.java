package ch.frostnova.keygen.alphabet.impl;

import ch.frostnova.keygen.alphabet.Alphabet;

import java.util.NoSuchElementException;

/**
 * Alphabet consisting of characters '0' to '9'
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class NumericAlphabet implements Alphabet {


    @Override
    public int length() {
        return 10;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= 10) {
            throw new IndexOutOfBoundsException();
        }
        return (char) ('0' + index);
    }

    @Override
    public int indexOf(char c) {
        if (c < '0' || c > '9') {
            throw new NoSuchElementException();
        }
        return c - '0';
    }
}
