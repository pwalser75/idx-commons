package ch.frostnova.keygen.model;

import ch.frostnova.util.check.Check;
import ch.frostnova.util.check.CheckString;

import java.util.regex.Pattern;

/**
 * Key pattern, for formatting keys. A key pattern contains hashes '#' and formatting characters. When formatting a
 * value, the pattern is iterated as follows:
 * <ul>
 * <li>if the next pattern char is '#', the next value character is output</li>
 * <li>if the next pattern char is not '#', the pattern character is output</li>
 * <li>if the next pattern char is '*' (only allowed at the end), the iteration is reset to the beginning of the
 * pattern</li>
 * <li>if the pattern is at the end, the remaining value characters will be output</li>
 * </ul>
 * <p>
 * Example: the pattern <code>####-*</code> would output the value <code>123456789</code> as <code>1234-5678-9</code>
 *
 * @author pwalser
 * @since 22.02.2017
 */
public class KeyPattern {

    public static final KeyPattern UUID = new KeyPattern("########-####-####-####-#############");
    public static final KeyPattern LICENSE_KEY = new KeyPattern("####-*");

    private final static Pattern FORMAT = Pattern.compile("([^\\*]*#[^\\*]*)+\\*?");

    private final String pattern;

    public KeyPattern(String pattern) {
        this.pattern = Check.required(pattern, "pattern", CheckString.format(FORMAT));
    }

    public String format(String value) {

        Check.required("value", value);
        char[] patternChars = pattern.toCharArray();
        StringBuilder builder = new StringBuilder();
        int patternOffset = 0;
        for (char c : value.toCharArray()) {
            while (patternOffset < patternChars.length && patternChars[patternOffset] != '#') {
                if (patternChars[patternOffset] == '*') {
                    patternOffset = 0;
                } else {
                    builder.append(patternChars[patternOffset]);
                    patternOffset++;
                }
            }
            builder.append(c);
            patternOffset++;
        }
        return builder.toString();
    }
}
