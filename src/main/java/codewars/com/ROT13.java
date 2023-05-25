package codewars.com;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.codewars.com/kata/52223df9e8f98c7aa7000062">ROT13</a>
 */
public class ROT13 {
    public static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {

        System.out.println(rot131("NnMmAaZz"));
    }

    public static String rot13(String message) {
        var r = message.chars().mapToObj(c ->
                        c >= 'A' && c <= 'Z'
                                ? Character.toString((c + 13 - 'A') % 26 + 'A')
                                : c >= 'a' && c <= 'z'
                                ? Character.toString((c + 13 - 'a') % 26 + 'a')
                                : Character.toString(c))
                .collect(Collectors.joining());
        System.out.println(r);
        return "";
    }

    public static String rot131(String message) {

        return Arrays.stream(message.split(""))
                .map(s -> {
                    if (ALPHABET_UPPER.contains(s)) {
                        int index = (ALPHABET_UPPER.indexOf(s) + 13) % 26;
                        return ALPHABET_UPPER.charAt(index) + "";
                    } else if (ALPHABET_LOWER.contains(s)) {
                        int index = (ALPHABET_LOWER.indexOf(s) + 13) % 26;
                        return ALPHABET_LOWER.charAt(index) + "";
                    } else {
                        return s;
                    }
                })
                .collect(Collectors.joining());
    }
}
