package codewars.com;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Numbers {

    /**
     * @see <a href="https://www.codewars.com/kata/647518391e258e80eedf6e06">Two Sets of Equal Sum</a>
     * @param n
     * @return
     */
    public static List<List<Integer>> createTwoSetsOfEqualSum(int n) {
        // Divide the numbers 1,2,…,n into two sets of equal sum.

        // NOTE: If it's not possible, return an List of two empty Lists, i.e. [ [ ], [ ] ].
        // The code below does not do this, because it returns [ ].
        // You need to create the two sets/lists yourself, even if they are empty.
        // 1 2 3 4 5 6 7=28
        // sum/2=14
        // possible sets=[1,2,5,6]=14[3,4,7]=14 [1,3,4,6]=14[2,5,7]=14
        final List<List<Integer>> sets = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        long sum = n * (n + 1L) / 2;
        System.out.println(sum);
        System.out.println(sum / 2);
        if ((sum & 1) == 1) {
            sets.add(list1);
            sets.add(list2);
            return sets;
        }
        long sum1 = 0;
        long sum2 = 0;
        for (int i = n; i >= 1; i--) {
            if (sum1 < sum2) {
                sum1 += i;
                list1.add(i);
            } else {
                sum2 += i;
                list2.add(i);
            }
        }
        System.out.println(sum1);
        System.out.println(sum2);
        sets.add(list1);
        sets.add(list2);
        return sets;
    }

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
