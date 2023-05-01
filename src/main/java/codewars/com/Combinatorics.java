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
}
