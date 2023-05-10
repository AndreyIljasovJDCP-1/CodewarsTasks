package codewars.com;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;


/**
 * @see <a href="https://www.codewars.com/kata/5a5032f4fd56cb958e00007a">TV Remote</a>
 * How many button presses on my remote are required to type a given word?
 */
public class TV_Remote {
    private static final Map<String, int[]> KEYBOARD = new TreeMap<>();

    static {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                KEYBOARD.put(Character.toString(i * 5 + j + 97), new int[] {i, j}); //
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                KEYBOARD.put(Character.toString(i * 3 + j + 49), new int[] {i, j + 5}); //
            }
        }
        KEYBOARD.put(".", new int[] {3, 5});
        KEYBOARD.put("@", new int[] {3, 6});
        KEYBOARD.put("0", new int[] {3, 7});
        KEYBOARD.put("z", new int[] {4, 5});
        KEYBOARD.put("_", new int[] {4, 6});
        KEYBOARD.put("/", new int[] {4, 7});
    }

    public static void main(String[] args) {
        System.out.println(tvRemote("solution"));
    }

    public static int tvRemote(final String word) {
        int[] current = new int[] {0, 0};
        int result = 0;
        for (String letter : word.split("")) {
            int[] next = KEYBOARD.get(letter);
            result += getDistance(current, next);
            current = next;
        }
        return result;
    }

    public static int tvRemote1(final String word) {
        var card = Arrays.stream(word.split("")).map(KEYBOARD::get).toList();
        int sum = getDistance(new int[] {0, 0}, card.get(0));
        for (int i = 1; i < card.size(); i++) {
            sum += getDistance(card.get(i - 1), card.get(i));
        }
        return sum;
    }

    private static int getDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + 1;
    }

}
