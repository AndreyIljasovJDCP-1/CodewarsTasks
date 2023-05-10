package codewars.com;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @see <a href="https://www.codewars.com/kata/52b7ed099cdc285c300001cd">
 *     Sum of Intervals
 *     </a>
 */
public class Arrays {

    public static int sumIntervals(int[][] intervals) {
        // Arrays.sort(intervals, Comparator.comparing(a -> a[0]));
        // Arrays.sort(intervals, (a, b) -> Integer.compare(b[1], a[1]));
        Comparator<int[]> comparator =
                (o1, o2) -> {
                    if (o1[0] == o2[0]) {
                        return Integer.compare(o1[1], o2[1]);
                    }
                    return Integer.compare(o1[0], o2[0]);
                };
        var sorted = java.util.Arrays.stream(intervals).sorted(comparator).toArray(int[][]::new);
        System.out.println(java.util.Arrays.deepToString(sorted));

        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i][1] >= sorted[i + 1][0]) {
                if (sorted[i][1] >= sorted[i + 1][1]) {
                    sorted[i + 1][0] = sorted[i][0];
                    sorted[i + 1][1] = sorted[i][1];
                    sorted[i][0] = 0;
                    sorted[i][1] = 0;
                } else {
                    sorted[i + 1][0] = sorted[i][0];
                    sorted[i][0] = 0;
                    sorted[i][1] = 0;
                }
            }
        }
        System.out.println(java.util.Arrays.deepToString(sorted));

        var intervalLength =
                java.util.Arrays.stream(sorted)
                        .filter(s -> !(s[0] == 0 && s[1] == 0))
                        .mapToInt(s -> Math.abs(s[1] - s[0]))
                        .sum();
        System.out.println(intervalLength);
        return -1;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/51edd51599a189fe7f000015">Mean Square Error </a>
     * @param arr1
     * @param arr2
     * @return average diff^2
     */
    public static double solution(int[] arr1, int[] arr2) {

        return 1.0
                * IntStream.range(0, arr1.length).map(i -> (arr1[i] - arr2[i]) * (arr1[i] - arr2[i])).sum()
                / arr1.length;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5526fc09a1bbd946250002dc">Find The Parity Outlier
     *     </a>
     */
    static int find(int[] integers) {
        boolean find = java.util.Arrays.stream(integers).map(x -> x & 1).limit(3).sum() < 2;
        return java.util.Arrays.stream(integers).filter(x -> (x & 1) == (find ? 1 : 0)).findFirst().getAsInt();
    }

    /**
     * @see <a href="https://www.codewars.com/kata/54d7660d2daf68c619000d95">Common Denominators</a>
     * @param lst
     * @return
     */
    public static String convertFractions(long[][] lst) {
        if (lst.length == 0) return "";
        simplify(lst);
        System.out.println(java.util.Arrays.deepToString(lst));
        var max = java.util.Arrays.stream(lst).mapToLong(x -> x[1]).max().getAsLong();
        int i = 1;
        while (true) {
            boolean flag = true;
            for (long[] fractions : lst) {
                flag &= max * i % fractions[1] == 0;
            }
            if (flag) break;
            i++;
        }

        long denominator = i * max;
        System.out.println(denominator + " - общий знаменатель");
        return java.util.Arrays.stream(lst)
                .map(
                        x -> String.format("(%d,%d)", x[0] * (denominator / x[1]), x[1] * (denominator / x[1])))
                .collect(Collectors.joining());
    }

    private static void simplify(long[][] list) {

        for (long[] fraction : list) {
            for (long j = fraction[0]; j > 1; j--) {
                if (fraction[1] % j == 0 && fraction[0] % j == 0) {
                    fraction[0] = fraction[0] / j;
                    fraction[1] = fraction[1] / j;
                    break;
                }
            }
        }
    }

    /**
     * @see <a href="https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1">Snail Sort</a>
     * @param array nxn
     * @return array in a clockwise snail-shell pattern.
     */
    public static int[] snail(int[][] array) {
        if (array.length == 0 || array[0].length == 0) return new int[] {};
        int n = array.length;
        int[] result = new int[n * n];
        int index = 0;
        int up = 0;
        int down = n - 1;
        int left = 0;
        int right = n - 1;
        while (index < n * n) {
            for (int i = up; i <= right; i++) result[index++] = array[up][i];
            up++;
            for (int i = up; i <= down; i++) result[index++] = array[i][right];
            right--;
            for (int i = right; i >= left; i--) result[index++] = array[down][i];
            down--;
            for (int i = down; i >= up; i--) result[index++] = array[i][left];
            left++;
        }
        return result;
    }
}
