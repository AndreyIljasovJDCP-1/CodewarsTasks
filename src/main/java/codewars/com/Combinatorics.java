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
}
