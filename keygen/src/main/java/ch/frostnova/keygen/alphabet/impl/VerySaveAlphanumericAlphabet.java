package ch.frostnova.keygen.alphabet.impl;

/**
 * Alphanumeric alphabet without any remotely similar characters (l,I,1, o,O,0 and B,8, s,S, z,Z,2, u,U, w,W, x,X)
 *
 * @author pwalser
 * @since 20.02.2017
 */
public class VerySaveAlphanumericAlphabet extends PredefinedAlphabet {

    public static void main(String[] args) {
        System.out.println(new AlphanumericAlphabet().allChars());
    }

    public VerySaveAlphanumericAlphabet() {
        super("345679ACDEFGHJKLMNPQRTVYabcdefghijkmnpqrtvy");
    }
}
