package codewars.com;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Regex {
    private static final String VERSION_REGEX = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+).*)?)?$";

    public static void main(String[] args) {

        Matcher matcher = Pattern.compile(VERSION_REGEX).matcher("01.2545.");
        matcher.find();

        String majorGroup = matcher.group(1);
        String minorGroup = matcher.group(2);
        String patchGroup = matcher.group(3);
        int major = Integer.parseInt(majorGroup);
        int minor = minorGroup == null ? 0 : Integer.parseInt(minorGroup);
        int patch = patchGroup == null ? 0 : Integer.parseInt(patchGroup);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/56af1a20509ce5b9b000001e">Salesman's Travel</a>
     */
    public static String travel(String r, String zipcode) {
        var map =
                Arrays.stream(r.split(","))
                        .map(s -> s.replaceAll("(\\d+) (.*) (\\w{2}\\s\\d{5})", "$3$2/$1"))
                        .collect(
                                Collectors.toMap(
                                        k -> k.substring(0, 8),
                                        v -> v.substring(8),
                                        (a, b) ->
                                                a.substring(0, a.indexOf("/"))
                                                        + ","
                                                        + b.substring(0, b.indexOf("/") + 1)
                                                        + a.substring(a.indexOf("/") + 1)
                                                        + ","
                                                        + b.substring(b.indexOf("/") + 1)));

        return zipcode + ":" + map.getOrDefault(zipcode, "/");
    }

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

    /**
     * @see <a href="https://www.codewars.com/kata/5277c8a221e209d3f6000b56">Valid Braces</a>
     * @param s parentheses, brackets and curly braces: ()[]{}
     * @return if the order of the braces is valid
     */
    public static boolean isValid(String s) {
        int x = s.length();
        s = s.replaceAll("\\(\\)|\\[\\]|\\{\\}", "");
        return s.length() != x && (s.length() == 0 || isValid(s));
    }

    public static boolean isValid1(String braces) {
        String in = braces;
        String out = braces;
        while (!(out = out.replaceAll("(\\Q()\\E)|(\\Q[]\\E)|(\\Q{}\\E)", "")).equals(in)) {
            in = out;
        }
        return in.equals(""); // your code here
    }
    /**
     * @see <a href="https://www.codewars.com/kata/526dbd6c8c0eb53254000110">Not very secure</a>
     * @param s
     * @return
     */
    public static boolean alphanumeric(String s) {
        System.out.println(s);
        // return s.length() != 0 && s.matches("[^\\W_]*");
        return s.matches("\\p{Alnum}+");
    }
}
