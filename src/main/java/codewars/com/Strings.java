package codewars.com;

import java.util.Arrays;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.*;

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
     * @see <a href="https://www.codewars.com/kata/5629db57620258aa9d000014">Strings Mix</a>
     * @param s1 "Are the kids at home? aaaaa fffff"
     * @param s2 "Yes they are here! aaaaa fffff"
     * @return "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh"
     */
    public static String mix(String s1, String s2) {
        var map1 =
                Arrays.stream(s1.split(""))
                        .filter(s -> s.matches("[a-z]"))
                        .collect(groupingBy(Function.identity(), summingInt(s -> 1)));
        var map2 =
                Arrays.stream(s2.split(""))
                        .filter(s -> s.matches("[a-z]"))
                        .collect(groupingBy(Function.identity(), summingInt(s -> 1)));
        var mapTransform1 =
                map1.entrySet().stream()
                        .filter(kv -> kv.getValue() > 1)
                        .collect(toMap(Map.Entry::getKey, kv -> "1:" + kv.getKey().repeat(kv.getValue())));
        var mapTransform2 =
                map2.entrySet().stream()
                        .filter(kv -> kv.getValue() > 1)
                        .collect(toMap(Map.Entry::getKey, kv -> "2:" + kv.getKey().repeat(kv.getValue())));
        var result =
                Stream.of(mapTransform1, mapTransform2)
                        .flatMap(map -> map.entrySet().stream())
                        .collect(
                                toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (v1, v2) ->
                                                v1.length() == v2.length()
                                                        ? v1.replaceAll("\\d", "=")
                                                        : v1.length() < v2.length() ? v2 : v1));

        return result.values().stream()
                .sorted(comparingInt(String::length).reversed().thenComparing(naturalOrder()))
                .collect(joining("/"));
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
        while (array[i] - array[++i] == -1);
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

    /**
     * @see <a href="https://www.codewars.com/kata/529eef7a9194e0cbc1000255">Anagram Detection</a>
     */
    public static boolean isAnagram(String test, String original) {
        test = Arrays.stream(test.toLowerCase().split("")).sorted().collect(Collectors.joining());
        original =
                Arrays.stream(original.toLowerCase().split("")).sorted().collect(Collectors.joining());

        return test.equals(original);
    }

    public static boolean isAnagram1(final String test, final String original) {
        return sortedLowercase(test).equals(sortedLowercase(original));
    }

    private static String sortedLowercase(final String s) {
        return s.toLowerCase()
                .chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static boolean isAnagram2(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        if (a == b) return true;

        return Arrays.equals(
                a.toLowerCase().chars().sorted().toArray(), b.toLowerCase().chars().sorted().toArray());
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5375f921003bf62192000746">
     *     Word a10n (abbreviation)</a>
     * @param string
     * @return
     */
    static String abbreviate(String string) {
        for (var s : string.split("[^a-zA-Z]")) {
            string =
                    string.replaceFirst(
                            s,
                            s.length() > 3
                                    ? "" + s.charAt(0) + (s.length() - 2) + s.substring(s.length() - 1)
                                    : s);
        }
        return string;
    }

    public static String myabbreviate(String string) {
        StringBuilder result = new StringBuilder();
        if (string.isEmpty()) return null;
        // String result = "";
        String word = "";
        boolean oneWord = true;
        for (char c : string.toCharArray()) {

            if (Character.isLetter(c)) {
                word += c;
            } else {
                if (word.length() > 3) {
                    result.append(encrypt(word)).append(c);
                    word = "";
                } else if (word.length() > 0) {
                    result.append(word).append(c);
                    word = "";
                } else {
                    result.append(c);
                }
                oneWord = false;
            }
        }

        return oneWord
                ? word.length() > 3 ? encrypt(word) : word
                : word.length() > 3
                ? result.append(encrypt(word)).toString()
                : word.length() > 0 ? result.append(word).toString() : result.toString();
    }

    private static String encrypt(String word) {

        return "" + word.charAt(0) + (word.length() - 2) + word.charAt(word.length() - 1);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/58daa7617332e59593000006">Most digits</a>
     * @param numbers
     * @return
     */
    public static int findLongest(int[] numbers) {
        int max = Integer.MIN_VALUE;
        int longestNum = 0;
        for (int num : numbers) {
            if (String.valueOf(Math.abs(num)).length() > max) {
                max = String.valueOf(num).length();
                longestNum = num;
            }
        }
        return longestNum;
    }

    public static int findLongestStream(int[] numbers) {
        return IntStream.of(numbers)
                .reduce(0, (a, b) -> String.valueOf(Math.abs(a)).length() >= String.valueOf(Math.abs(b)).length()
                        ? a
                        : b);
    }
}
