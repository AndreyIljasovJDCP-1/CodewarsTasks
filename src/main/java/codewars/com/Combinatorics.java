package codewars.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.codewars.com/kata/5263c6999e0f40dee200059d">
 *     The observed PIN
 *     </a>
 */
public class Combinatorics {
    public static final int[][] POSSIBLE_NUMBERS =
            new int[][] {
                    {0, 8},
                    {1, 2, 4},
                    {1, 2, 3, 5},
                    {2, 3, 6},
                    {1, 4, 5, 7},
                    {2, 4, 5, 6, 8},
                    {3, 5, 6, 9},
                    {4, 7, 8},
                    {0, 5, 7, 9, 8},
                    {6, 8, 9}
            };

    public static void main(String[] args) {
        getPINs("11");
    }

    public static List<String> getPINs(String observed) {
        Arrays.stream(POSSIBLE_NUMBERS).forEach(s -> System.out.println(Arrays.toString(s)));
        int[] pin = new int[observed.length()];
        List<String> result = new ArrayList<>();
        pins(0, pin, observed, result);
        result.forEach(System.out::println);
        return result;
    }

    private static void pins(int index, int[] pin, String observed, List<String> arrayList) {
        if (index == pin.length) {
            System.out.println("PIN: " + Arrays.toString(pin));
            arrayList.add(Arrays.stream(pin).mapToObj(String::valueOf).collect(Collectors.joining()));

        } else {
            int digit = observed.charAt(index) - 48;
            for (int i = 0; i < POSSIBLE_NUMBERS[digit].length; i++) {
                pin[index] = POSSIBLE_NUMBERS[digit][i];
                pins(index + 1, pin, observed, arrayList);
            }
        }
    }

    /**
     * @see <a href="https://www.codewars.com/kata/555624b601231dc7a400017a">Josephus Survivor</a>
     * @param n total
     * @param k step
     * @return last standing
     */
    public static int josephusSurvivor(final int n, final int k) {
        if (n == 1) return 1;
        return (josephusSurvivor(n - 1, k) + k - 1) % n + 1;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5550d638a99ddb113e0000a2">Josephus Permutation</a>
     * @param items initial
     * @param k step
     * @return sequence in order out
     * @param <T> any type
     */
    public static <T> List<T> josephusPermutationBest(final List<T> items, final int k) {
        if (items.isEmpty()) return List.of();
        List<T> result = new ArrayList<>();
        int index = 0;
        while (items.size() > 0) {
            index = (index + k - 1) % items.size();
            result.add(items.remove(index));
        }
        return result;
    }

    public static <T> List<T> josephusPermutation(final List<T> items, final int k) {
        if (items.isEmpty()) return List.of();
        List<T> result = new ArrayList<>();
        boolean[] death = new boolean[items.size()];
        int count = 0;
        while (result.size() < items.size()) {
            for (int i = 0; i < items.size(); i++) {
                if (!death[i]) count++;
                if (count == k) {
                    death[i] = true;
                    result.add(items.get(i));
                    count = 0;
                }
            }
        }
        return result;
    }

    /**
     * Сочетания. Комбинаторика. Найти все сочетания n по m
     * Вычислить сумму сочетаний, вернуть максимально близкую <=t
     * @param t предел
     * @param k размер сочетания
     * @param ls список расстояний
     * @return
     */
    public static Integer chooseBestSum(int t, int k, List<Integer> ls) {

        List<Integer> sumList = new ArrayList<>();
        int[] combination = new int[k];
        fillSumList(0, -1, k, combination, sumList, ls);
        sumList.forEach(System.out::println);
        return sumList.stream().filter(x -> x <= t).max(Integer::compareTo).orElse(-1);
    }

    private static void fillSumList(
            int index, int maxUsed, int k, int[] combination, List<Integer> sumList, List<Integer> ls) {

        if (index == k) {
            System.out.println(Arrays.toString(combination));
            sumList.add(Arrays.stream(combination).map(ls::get).sum());
        } else {
            for (int i = maxUsed + 1; i < ls.size(); i++) {
                combination[index] = i;
                fillSumList(index + 1, i, k, combination, sumList, ls);
            }
        }
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5254ca2719453dcc0b00027d">
     *     So Many Permutations!
     *     </a>
     * @see <a href="https://www.techiedelight.com/ru/generate-permutations-string-java-recursive-iterative">
     *     Генерировать все перестановки строки в Java — рекурсивно и итеративно
     *     </a>
     *
     * @param s "ABC"
     * @return ABC, ACB, BAC, BCA, CBA, CAB
     */
    public static List<String> singlePermutations(String s) {

        List<String> result = new ArrayList<>();
        String[] seq = new String[s.length()];
        //combination(0, seq, result, s);
        // permutations(0, s.split(""), result);
        permutationsStr("", s, result);
        return result.stream().distinct().collect(Collectors.toList());
    }

    // без массива, с 2 строками
    private static void permutationsStr(String candidate, String remaining, List<String> result) {

        if (remaining == null) {
            return;
        }

        if (remaining.length() == 0) {
            System.out.println(candidate);
            result.add(candidate);
        }

        for (int i = 0; i < remaining.length(); i++) {
            String newCandidate = candidate + remaining.charAt(i);
            String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
            permutationsStr(newCandidate, newRemaining, result);
        }
    }

    // Рекурсивная функция для генерации всех перестановок строки с массивом String[]
    private static void permutations(int currentIndex, String[] chars, List<String> result) {
        if (currentIndex == chars.length - 1) {
            System.out.println(Arrays.toString(chars));
            result.add(String.join("", chars));
        }

        for (int i = currentIndex; i < chars.length; i++) {
            swap(chars, currentIndex, i);
            permutations(currentIndex + 1, chars, result);
            swap(chars, currentIndex, i);
        }
    }

    private static void swap(String[] chars, int i, int j) {
        String temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // all permutations with duplicates aaa,aab....
    private static void combination(int index, String[] seq, List<String> result, String input) {
        if (index == seq.length) {
            System.out.println("вывод окончательный: " + Arrays.toString(seq));
            result.add(String.join("", seq));

        } else {
            for (int i = 0; i < seq.length; i++) {
                seq[index] = String.valueOf(input.charAt(i));
                combination(index + 1, seq, result, input);
            }
        }
    }

}
