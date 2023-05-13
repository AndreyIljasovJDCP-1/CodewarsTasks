package codewars.com;

import java.util.Arrays;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Strings {

    public static void main(String[] args) {
        System.out.println(checkString("МАКАРОШКИ", "АКРИ"));
        System.out.println(stripComments("apples, pears # and bananas\ngrapes\t\nbananas !apples", new String[]{"#", "!"}));
    }

    /**
     * Check if you can build the "search" from text in order of letters only
     *
     * @param text
     * @param search
     * @return
     */
    private static boolean checkString(String text, String search) {
        for (String symbol : search.split("")) {
            if (!text.contains(symbol)) {
                return false;
            }
            System.out.println("text: " + text + " symbol: " + symbol);
            text = text.substring(text.indexOf(symbol) + 1);
            System.out.println("new text: " + text);
        }
        return true;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/55c04b4cc56a697bb0000048">
     *     Scramblies</a>
     * @param str1
     * @param str2
     * @return
     */
    public static boolean scramble1(String str1, String str2) {
        Map<String, Long> str1Map =
                Stream.of(str1.split("")).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        Map<String, Long> str2Map =
                Stream.of(str2.split("")).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return str2Map.entrySet().stream()
                .allMatch(e -> str1Map.get(e.getKey()) != null && e.getValue() <= str1Map.get(e.getKey()));
    }

    public static boolean scramble(String str1, String str2) {
        if (str2.isEmpty()) return true;
        var mapLetters =
                java.util.Arrays.stream(str1.split(""))
                        .collect(Collectors.toMap(Function.identity(), s -> 1, Integer::sum, TreeMap::new));
        var mapWord =
                java.util.Arrays.stream(str2.split(""))
                        .collect(Collectors.toMap(Function.identity(), s -> 1, Integer::sum, TreeMap::new));
        boolean noLetter = mapWord.keySet().stream().anyMatch(s -> !mapLetters.containsKey(s));
        if (noLetter) return false;
        boolean notEnoughLetters =
                mapWord.entrySet().stream()
                        .anyMatch(entry -> entry.getValue() > mapLetters.get(entry.getKey()));
        return !notEnoughLetters;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5839edaa6754d6fec10000a2">
     *     Find the missing letter
     *     </a>
     * @param array
     * @return
     */
    public static char findMissingLetter(char[] array) {
        int i = 0;
        while (array[i] - array[++i] == -1)
            ;
        return (char) (array[i] - 1);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/55c6126177c9441a570000cc">
     *     Weight for weight
     *     </a>
     * @param strng
     * @return
     */
    public static String orderWeight(String strng) {

        return strng.isEmpty()
                ? ""
                : Arrays.stream(strng.split(" "))
                .sorted(Comparator.comparing(Strings::sumDigits).thenComparing(String::compareTo))
                .collect(Collectors.joining(" "));
    }

    private static Integer sumDigits(String s) {
        return s.chars().map(c -> c - 48).sum();
    }

    /**
     * @see <a href="https://www.codewars.com/kata/51c8e37cee245da6b40000bd">Strip Comments</a>
     * @param text
     * @param commentSymbols
     * @return text without comments
     */
    public static String stripCommentsBest(String text, String[] commentSymbols) {
        return text.replaceAll(" *([" + String.join("", commentSymbols) + "].*)?(\n|$)", "$2");
    }

    public static String stripComments(String text, String[] commentSymbols) {
        System.out.println(text);
        List<String> result = new ArrayList<>();
        for (String words : text.split("\n")) {
            for (String comment : commentSymbols) {
                if (words.contains(comment)) {
                    words = words.substring(0, words.indexOf(comment));
                }
            }
            result.add(words.stripTrailing());
        }
        System.out.println(result);
        return String.join("\n", result);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/559536379512a64472000053">
     *     Playing with passphrases</a>
     * @param s text
     * @param n offset
     * @return encoding string
     */
    public static String playPass(String s, int n) {
        s = s.toUpperCase();
        StringBuilder res = new StringBuilder();
        int index = 0;
        // A-Z=>65-90 0-9=>48-57
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                res.append((char) 57 - c);
            } else if (Character.isLetter(c)) {
                String letter = Character.toString((c + n - 65) % 26 + 65);
                res.append(index % 2 == 0 ? letter : letter.toLowerCase());
            } else {
                res.append(c);
            }
            index++;
        }
        return res.reverse().toString();
    }
}
