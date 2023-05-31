package codewars.com;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sequence {
    public static void main(String[] args) {

    }

    /**
     * @param begin
     * @return
     * @see <a href="https://www.codewars.com/kata/59971e64bfccc70748000068">Sequence convergence</a>
     */
    public static int convergence(int begin) {
        int stepTest = 0;
        int base = 1;
        int test = begin;
        while (true) {
            if (base == test) {
                return stepTest;
            } else if (base < test) {
                base = next(base);
            } else {
                test = next(test);
                stepTest++;
            }
        }
    }

    private static int next(int n) {
        return n
                + String.valueOf(n)
                .chars()
                .map(Character::getNumericValue)
                .filter(c -> c != 0)
                .reduce((a, b) -> a * b)
                .orElse(n);
    }

    /**
     * @param n
     * @return BigInteger U(n)
     * @see <a href="https://www.codewars.com/kata/563f0c54a22b9345bf000053">
     * A disguised sequence(I)</a>
     */
    public static BigInteger fcn(int n) {
        return n == 0 ? BigInteger.ONE : BigInteger.TWO.shiftLeft(n - 1);
    }

    /**
     * @return IntStream Fibonacci
     * @see <a href="https://www.codewars.com/kata/55695bc4f75bbaea5100016b">Fibonacci Streaming</a>
     */
    public static IntStream generateFibonacciSequence() {

        return Stream.iterate(new int[]{0, 1}, a -> new int[]{a[1], a[0] + a[1]})
                .limit(46)
                .mapToInt(a -> a[1]);
    }

    /**
     * generate sequence
     */
    private static int[] generateSeq(int base, int limit) {
        return IntStream.iterate(
                        base,
                        n -> n <= limit,
                        n -> {
                            if (String.valueOf(n).length() == 1) {
                                return n + n;
                            } else {
                                return n
                                        + String.valueOf(n)
                                        .chars()
                                        .filter(c -> c != 48)
                                        .map(c -> c - 48)
                                        .reduce((a, b) -> (a) * (b))
                                        .orElse(0);
                            }
                        })
                .toArray();
    }
}
