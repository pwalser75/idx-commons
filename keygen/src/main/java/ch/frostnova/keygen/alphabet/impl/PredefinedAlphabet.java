package ch.frostnova.keygen.alphabet.impl;

import ch.frostnova.keygen.alphabet.Alphabet;
import ch.frostnova.util.check.Check;
import ch.frostnova.util.check.CheckString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * TODO: add Javadoc comment
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class PredefinedAlphabet implements Alphabet {

    private final List<Character> characters = new ArrayList<>();

    public PredefinedAlphabet(String stringWithCharacters) {
        Check.required(stringWithCharacters, stringWithCharacters, CheckString.min(1));
        for (char c : stringWithCharacters.toCharArray()) {
            if (!characters.contains(c)) {
                characters.add(c);
            }
        }
        if (characters.size() < 2) {
            throw new IllegalArgumentException("Alphabet must consist of at least two distinct characters");
        }
    }

    @Override
    public int length() {
        return characters.size();
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException();
        }
        return characters.get(index);
    }

    @Override
    public int indexOf(char c) {
        int index = characters.indexOf(c);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return index;
    }
}
