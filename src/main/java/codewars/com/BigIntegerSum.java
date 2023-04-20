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

    /**
     * @see <a href="https://www.codewars.com/kata/5511b2f550906349a70004e1">Last digit of a
     *     largenumber </a>
     * @param n1 BigInteger
     * @param n2 power BigInteger
     * @return last digit of result n1 pow n2
     */
    public static int lastDigitBest(BigInteger n1, BigInteger n2) {
        return n1.modPow(n2, BigInteger.TEN).intValue();
    }

    public static int lastDigit(BigInteger n1, BigInteger n2) {
        String stringBigDigit = n1.toString();
        int lastChar = n1.toString().charAt(stringBigDigit.length() - 1) - 48;
        boolean odd = n2.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0;
        System.out.println(odd ? "нечет" : "чет"); // чет нечет
        if (n2.compareTo(BigInteger.ZERO) == 0) return 1;
        var remainderFour = n2.mod(BigInteger.valueOf(4));
        boolean compareToZero = remainderFour.compareTo(BigInteger.ZERO) == 0;
        boolean compareToOne = remainderFour.compareTo(BigInteger.ONE) == 0;
        switch (lastChar) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return odd
                        ? remainderFour.compareTo(BigInteger.valueOf(3)) == 0 ? 8 : 2
                        : compareToZero ? 6 : 4;
            case 3:
                return odd ? compareToOne ? 3 : 7 : compareToZero ? 1 : 9;
            case 4:
                return odd ? 4 : 6;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return odd ? compareToOne ? 7 : 3 : compareToZero ? 1 : 9;
            case 8:
                return odd ? compareToOne ? 8 : 2 : compareToZero ? 6 : 4;
            case 9:
                return odd ? 9 : 1;
            default:
                return -1;
        }
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
