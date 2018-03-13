package ch.frostnova.keygen.alphabet;

/**
 * An alphabet consists of an ordered set of characters (at least two).
 *
 * @author pwalser
 * @since 20.02.2017
 */
public interface Alphabet {

    int length();

    char charAt(int index);

    int indexOf(char c);

    default String allChars() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length(); i++) {
            builder.append(charAt(i));
        }
        return builder.toString();
    }


}
