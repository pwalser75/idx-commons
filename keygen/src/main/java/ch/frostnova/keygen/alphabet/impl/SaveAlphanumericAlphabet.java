package ch.frostnova.keygen.alphabet.impl;

/**
 * Alphanumeric alphabet without similar characters (l,I,1, o,O,0 and B,8)
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class SaveAlphanumericAlphabet extends PredefinedAlphabet {

    public static void main(String[] args) {
        System.out.println(new AlphanumericAlphabet().allChars());
    }

    public SaveAlphanumericAlphabet() {
        super("2345679ACDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz");
    }
}
