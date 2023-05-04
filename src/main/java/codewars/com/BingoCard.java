package codewars.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BingoCard {
    private static final int[] NUMBERS = new int[75];
    private static final boolean[] NUMBERS_TAKEN = new boolean[75];
    private static final String[] BINGO = new String[]{"B", "I", "N", "G", "O"};
    private static final Random RANDOM = new Random();

    static {
        IntStream.range(0, 75).forEach(i -> NUMBERS[i] = i + 1);
    }

    public static void main(String[] args) {
        //System.out.println(Arrays.toString(RANDOM.ints(1, 16).distinct().limit(5).toArray()));
        System.out.println(Arrays.toString(getCard()));
        System.out.println(Arrays.toString(getBingo()));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/566d5e2e57d8fae53c00000c">Bingo Card </a>
     */
    public static String[] getCard() {
        String[] result = new String[24];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            int count = 0;
            int min = i * 15;
            int max = min + 15;
            int maxCount = i == 2 ? 4 : 5;
            while (count < maxCount) {
                var number = RANDOM.nextInt(max - min) + min;
                if (!NUMBERS_TAKEN[number]) {
                    result[index++] = BINGO[i] + NUMBERS[number];
                    count++;
                    NUMBERS_TAKEN[number] = true;
                }
            }
        }
        return result;
    }

    private static String[] getBingo() {
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int letter = i;
            int min = i * 15 + 1;
            int max = min + 15;
            result.add(RANDOM.ints(min, max)
                    .distinct()
                    .limit(i == 2 ? 4 : 5)
                    .mapToObj(x -> String.format("%s%d", BINGO[letter], x))
                    .toArray(String[]::new));
        }
        return result.stream().flatMap(Arrays::stream).toArray(String[]::new);
    }
}
