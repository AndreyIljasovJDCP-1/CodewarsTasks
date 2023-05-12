package codewars.com;

import java.math.BigInteger;
import java.util.stream.Stream;

/**
 * @see <a href="https://www.codewars.com/kata/60d2325592157c0019ee78ed">
 *     Sum the nums, sum the sums and sum the nums up to that sum
 *     </a>
 */
public class BigIntegerSum {

    public static void main(String[] args) {
        System.out.println(sum(4));
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
        return switch (lastChar) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> odd
                    ? remainderFour.compareTo(BigInteger.valueOf(3)) == 0 ? 8 : 2
                    : compareToZero ? 6 : 4;
            case 3 -> odd ? compareToOne ? 3 : 7 : compareToZero ? 1 : 9;
            case 4 -> odd ? 4 : 6;
            case 5 -> 5;
            case 6 -> 6;
            case 7 -> odd ? compareToOne ? 7 : 3 : compareToZero ? 1 : 9;
            case 8 -> odd ? compareToOne ? 8 : 2 : compareToZero ? 6 : 4;
            case 9 -> odd ? 9 : 1;
            default -> -1;
        };
    }

    /**
     * @see <a href="https://www.codewars.com/kata/62aab749ee78500065e39c00">
     *     Sum of Odd Triangle Rows
     *     </a>
     * @param n row of triangle
     * @return sum
     */
    public static BigInteger sum(int n) {
        BigInteger number = new BigInteger(String.valueOf(n));
        BigInteger sumSeq = number.add(BigInteger.ONE).multiply(number).divide(BigInteger.TWO);
        System.out.println(sumSeq);
        return sumSeq.multiply(sumSeq);//?!
    }

    /**
     * @see <a href="https://www.codewars.com/kata/559a28007caad2ac4e000083">
     *     Perimeter of squares in a rectangle</a>
     * @param n BigInteger
     * @return perimeter
     */
    public static BigInteger perimeter(BigInteger n) {

        return Stream.iterate(
                        new BigInteger[] {BigInteger.ZERO, BigInteger.ONE},
                        x -> new BigInteger[] {x[1], x[0].add(x[1])})
                .limit(n.longValue() + 1)
                .map(x -> x[1])
                .reduce(BigInteger.ZERO, BigInteger::add)
                .multiply(BigInteger.valueOf(4));
    }

    public static BigInteger perimeterBest(BigInteger n) {

        BigInteger a;
        BigInteger b = BigInteger.ONE;
        BigInteger c = BigInteger.ONE;
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i <= n.intValue(); i++) {
            a = b;
            b = c;
            c = a.add(b);
            sum = sum.add(a);
        }

        return sum.multiply(BigInteger.valueOf(4));
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
