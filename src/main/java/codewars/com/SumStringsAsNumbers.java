package codewars.com;

import java.util.Arrays;

public class SumStringsAsNumbers {

    public static void main(String[] args) {

    }
    /**
     * @see <a href="https://www.codewars.com/kata/5324945e2ece5e1f32000370">Sum Strings as
     *     Numbers</a>
     *     without using BigInteger
     */
    public static String sumStringsBuilder(String a, String b) {
        a = a.replaceAll("^0+", "");
        b = b.replaceAll("^0+", "");
        int sizeMax = Math.max(a.length(), b.length());
        a = "0".repeat(sizeMax - a.length()) + a;
        b = "0".repeat(sizeMax - b.length()) + b;

        int carry = 0;
        StringBuilder result = new StringBuilder();
        for (int i = sizeMax - 1; i >= 0; i--) {
            int sum = a.charAt(i) + b.charAt(i) - 96;
            result.insert(0, (sum + carry) % 10);
            carry = (sum + carry) / 10;
        }

        return carry == 1 ? 1 + result.toString() : result.toString();
    }

    public static String sumStrings(String a, String b) {
        a = a.replaceAll("^0+", "");
        b = b.replaceAll("^0+", "");
        int sizeMax = Math.max(a.length(), b.length());
        int sizeMin = Math.min(a.length(), b.length());
        char[] arrA, arrB;

        if (a.length() < b.length()) {
            arrA = new char[sizeMax];
            java.util.Arrays.fill(arrA, '0');
            System.arraycopy(a.toCharArray(), 0, arrA, sizeMax - sizeMin, sizeMin);
            arrB = b.toCharArray();
        } else if (a.length() > b.length()) {
            arrA = a.toCharArray();
            arrB = new char[sizeMax];
            Arrays.fill(arrB, '0');
            System.arraycopy(b.toCharArray(), 0, arrB, sizeMax - sizeMin, sizeMin);
        } else {
            arrA = a.toCharArray();
            arrB = b.toCharArray();
        }

        int offset = 0;
        char[] sum = new char[sizeMax + 1];
        for (int i = sizeMax - 1; i >= 0; i--) {
            int tempSum = arrA[i] + arrB[i] - 96 + offset;
            if (tempSum >= 10) {
                offset = 1;
                tempSum -= 10;
            } else {
                offset = 0;
            }
            sum[i + 1] = (char) (tempSum + '0');
        }
        sum[0] = offset == 1 ? (char) (1 + '0') : '0';
        System.out.println(sum);
        return offset == 0 ? String.valueOf(sum, 1, sizeMax) : String.valueOf(sum);
    }

    static String sumStrings1(String a, String b) {
        int length = Math.max(a.length(), b.length()) + 1;
        a = "0".repeat(length - a.length()) + a;
        b = "0".repeat(length - b.length()) + b;

        var result = new StringBuilder(length);
        for (int r = 0, i = length - 1; i >= 0; i--) {
            result.insert(0, (r = a.charAt(i) + b.charAt(i) - 96 + (r / 10)) % 10);
        }
        return result.toString().replaceFirst("0*", "");
    }
}
