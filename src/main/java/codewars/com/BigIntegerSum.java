package codewars.com;

import java.math.BigInteger;

/**
 * @see <a href="https://www.codewars.com/kata/60d2325592157c0019ee78ed">
 *     Sum the nums, sum the sums and sum the nums up to that sum
 *     </a>
 */
public class BigIntegerSum {

    public static void main(String[] args) {

    }
    /**
     * S(1) + S(2) + ... + S(N) = (N*(N+1)*(N+2))/6
     * S(N) = (N*(N+1))/2
     * Z(3) = 1 + 3 + 6 = 10
     * S(Z(3)) = S(10) = 55
     * */
    public static BigInteger sumOfSumsRight(int n) {

        BigInteger limit = BigInteger.valueOf(n);
        BigInteger total =
                limit
                        .multiply(limit.add((BigInteger.ONE)))
                        .multiply(limit.add((BigInteger.TWO)))
                        .divide(BigInteger.valueOf(6));
        System.out.println("total:" + total);
        return total.multiply(total.add(BigInteger.ONE)).divide(BigInteger.TWO);
    }

    private static long sumSquares(int n) {
        return n * (n + 1) * (2L * n + 1) / 6;
    }

    private static long sumFactor(int n) {
        return n * (2 + 2L * (n - 1)) / 2;
    }

    private static long sumSeq(int n) {

        return (long) (n + 2) * (n) * (n + 1) / 6;
    }
}
