package codewars.com;

import java.util.stream.LongStream;

public class Numbers {
    /**
     * @see <a href="https://www.codewars.com/kata/5287e858c6b5a9678200083c">Narcissistic Number</a>
     * @param number
     * @return
     */
    public static boolean isNarcissistic(int number) {
        String num = Integer.toString(number);
        return num.chars().map(c -> (int) Math.pow((c - 48), num.length())).sum() == number;
    }

    public static boolean isNarcissisticRec(int number) {
        return sum(number, Integer.toString(number).length()) == number;
    }

    private static int sum(int number, int pow) {
        if (number == 0) return 0;
        return (int) (Math.pow(number % 10, pow) + sum(number / 10, pow));
    }

    public static long sumCubes(int n) {
        return LongStream.rangeClosed(1, n).map(x -> x * x * x).sum();
    }
}
