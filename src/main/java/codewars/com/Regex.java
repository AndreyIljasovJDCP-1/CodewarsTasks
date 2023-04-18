package codewars.com;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Regex {

    /**
     * @see <a href="https://www.codewars.com/kata/5f7c38eb54307c002a2b8cc8">
     *     Remove the parentheses
     *     </a>
     */
    public static String removeParentheses(final String str) {
        String updated = str.replaceAll("\\([^()]*\\)", "");
        if (updated.contains("(")) updated = removeParentheses(updated);
        return updated;
    }
    public static String removeParenthesesMy(final String str) {
        String in = str;
        String out = str;
        while (!(out = out.replaceAll("(\\([^()]*\\))", "")).equals(in)) {
            in = out;
        }
        return out + "spaces"; // your code here
    }

    /**
     * @see <a href="https://www.codewars.com/kata/525f50e3b73515a6db000b83">
     *     Create Phone Number
     *     </a>
     * @param numbers arrays of digits of telephone number
     * @return formatted telephone number
     */
    public static String createPhoneNumberRegex(int[] numbers) {
        return Arrays.toString(numbers)
                .replaceAll("[,\\[\\]\\s+]", "")
                .replaceAll("(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3");
    }

    /**
     * @see <a href="https://www.codewars.com/kata/515de9ae9dcfc28eb6000001">
     *     splits the string into pairs
     *     </a>
     * @param s abcdef
     * @return [ab, cd, ef]
     */
    public static String[] solution(String s) {
        return s.length() % 2 == 0
                ? s.split("(?<=\\G.{2})")
                : (s + "_").split("(?<=\\G.{2})");
    }

    public static String pigIt(String str) {
        return Arrays.stream(str.split(" "))
                .map(word -> word.substring(1) + word.charAt(0) + "ay")
                .collect(Collectors.joining(" "))
                .replaceAll("(\\p{P})(ay)", "$1");
    }
}
